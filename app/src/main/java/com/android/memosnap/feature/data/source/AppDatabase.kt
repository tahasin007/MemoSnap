package com.android.memosnap.feature.data.source

import android.app.Application
import android.content.ContentValues
import androidx.compose.ui.graphics.toArgb
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.memosnap.core.theme.DodgerBlue
import com.android.memosnap.feature.dailytask.data.Converters
import com.android.memosnap.feature.dailytask.data.source.CategoryDao
import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TaskCategory
import com.android.memosnap.feature.note.data.NoteConverters
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.data.source.NoteTagDao
import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef
import com.google.gson.Gson
import com.google.gson.JsonArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Database(
    entities = [
        Note::class,
        NoteTag::class,
        NoteTagCrossRef::class,
        Task::class,
        Category::class,
        ChecklistItem::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class, NoteConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val noteTagDao: NoteTagDao
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "notes_db"

        // ── Existing migrations ──────────────────────────────────────────────
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE note ADD COLUMN imageUri TEXT")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE note ADD COLUMN imageData BLOB")
            }
        }

        // ── Phase 2 migration: expand note + migrate tasks ───────────────────
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {

                // 1. Add new columns to note table
                db.execSQL("ALTER TABLE note ADD COLUMN categoryId INTEGER")
                db.execSQL("ALTER TABLE note ADD COLUMN priority TEXT NOT NULL DEFAULT 'NONE'")
                db.execSQL("ALTER TABLE note ADD COLUMN isCompleted INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE note ADD COLUMN completedAt INTEGER")
                db.execSQL("ALTER TABLE note ADD COLUMN dueAt INTEGER")
                db.execSQL("ALTER TABLE note ADD COLUMN reminderAt INTEGER")
                db.execSQL("ALTER TABLE note ADD COLUMN noteType TEXT NOT NULL DEFAULT 'REGULAR'")

                // 2. Create checklist_item table (matches ChecklistItem @Entity exactly)
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `checklist_item` (
                        `noteId`      INTEGER NOT NULL,
                        `text`        TEXT    NOT NULL,
                        `isCompleted` INTEGER NOT NULL DEFAULT 0,
                        `sortOrder`   INTEGER NOT NULL DEFAULT 0,
                        `id`          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        FOREIGN KEY(`noteId`) REFERENCES `Note`(`id`)
                            ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_checklist_item_noteId` " +
                            "ON `checklist_item` (`noteId`)"
                )

                // 3. Migrate task rows → note rows, subtasks → checklist_item rows
                val gson = Gson()
                val defaultColor = DodgerBlue.toArgb()
                val dateCreated = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH)
                    .format(Date())

                val cursor = db.query("SELECT * FROM task")
                cursor.use {
                    while (it.moveToNext()) {
                        val name = it.getString(it.getColumnIndexOrThrow("name")) ?: ""
                        val taskNote = it.getString(it.getColumnIndexOrThrow("taskNote")) ?: ""
                        val catIdx = it.getColumnIndexOrThrow("categoryId")
                        val categoryId: Long? =
                            if (it.isNull(catIdx)) null else it.getLong(catIdx)
                        val priority =
                            it.getString(it.getColumnIndexOrThrow("priority")) ?: "NONE"
                        val isCompleted = it.getInt(it.getColumnIndexOrThrow("isCompleted"))
                        val subTasksJson =
                            it.getString(it.getColumnIndexOrThrow("sub_tasks"))

                        // Insert the task as a CHECKLIST note
                        val noteValues = ContentValues().apply {
                            put("title", name)
                            put("content", taskNote)
                            put("dateCreated", dateCreated)
                            put("color", defaultColor)
                            put("isPinned", 0)
                            put("isArchived", 0)
                            if (categoryId != null) put("categoryId", categoryId)
                            else putNull("categoryId")
                            put("priority", priority)
                            put("isCompleted", isCompleted)
                            put("noteType", "CHECKLIST")
                            // imageData, completedAt, dueAt, reminderAt → NULL (default)
                        }
                        // CONFLICT_REPLACE = 5; we use IGNORE (4) to skip on any edge-case clash
                        val newNoteId = db.insert("note", 4, noteValues)

                        // Insert checklist items from the serialised sub_tasks JSON
                        if (newNoteId != -1L &&
                            !subTasksJson.isNullOrEmpty() &&
                            subTasksJson != "null"
                        ) {
                            try {
                                val array = gson.fromJson(subTasksJson, JsonArray::class.java)
                                array?.forEachIndexed { index, element ->
                                    val obj = element.asJsonObject
                                    val text = obj.get("subTaskName")?.asString ?: ""
                                    val done = obj.get("isCompleted")?.asBoolean ?: false

                                    val itemValues = ContentValues().apply {
                                        put("noteId", newNoteId)
                                        put("text", text)
                                        put("isCompleted", if (done) 1 else 0)
                                        put("sortOrder", index)
                                    }
                                    db.insert("checklist_item", 4, itemValues)
                                }
                            } catch (_: Exception) {
                                // Skip malformed JSON — prefer data loss over migration crash
                            }
                        }
                    }
                }
            }
        }

        // ── Phase 4 migration: drop task table (activate after task UI is removed) ──
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS `task`")
            }
        }

        // ── onCreate: seed default categories using direct SQL ────────────────
        val DB_CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Use direct SQL — no nested Room instance required
                TaskCategory.entries.forEach { category ->
                    db.execSQL(
                        "INSERT OR IGNORE INTO `Category` (`name`) VALUES ('${category.category}')"
                    )
                }
            }
        }

        fun getCallback(application: Application) = DB_CALLBACK
    }
}