package com.android.memosnap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.memosnap.feature.note.data.NoteConverters
import com.android.memosnap.feature.note.data.model.CategoryEntity
import com.android.memosnap.feature.note.data.model.ChecklistItemEntity
import com.android.memosnap.feature.note.data.model.NoteEntity
import com.android.memosnap.feature.note.data.model.NoteTagCrossRef
import com.android.memosnap.feature.note.data.model.NoteTagEntity
import com.android.memosnap.feature.note.data.source.CategoryDao
import com.android.memosnap.feature.note.data.source.ChecklistItemDao
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.data.source.NoteTagDao

@Database(
    entities = [
        NoteEntity::class,
        NoteTagEntity::class,
        NoteTagCrossRef::class,
        CategoryEntity::class,
        ChecklistItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(NoteConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val noteTagDao: NoteTagDao
    abstract val categoryDao: CategoryDao
    abstract val checklistItemDao: ChecklistItemDao

    companion object {
        const val DATABASE_NAME = "notes_db"

        val DB_CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                listOf("Work", "Personal", "Home", "Birthday", "Other").forEach { category ->
                    db.execSQL(
                        "INSERT OR IGNORE INTO `Category` (`name`) VALUES ('$category')"
                    )
                }
            }
        }

        fun getCallback() = DB_CALLBACK
    }
}
