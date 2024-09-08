package com.android.memosnap.feature.data.source

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.memosnap.feature.dailytask.data.Converters
import com.android.memosnap.feature.dailytask.data.source.CategoryDao
import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.presentation.TaskCategory
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.data.source.NoteTagDao
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class, NoteTag::class, NoteTagCrossRef::class, Task::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val noteTagDao: NoteTagDao
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao


    companion object {
        const val DATABASE_NAME = "notes_db"

        fun getCallback(application: Application) = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    // Get the database instance and CategoryDao
                    val database = Room.databaseBuilder(
                        application,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    val categoryDao = database.categoryDao

                    // Insert default categories
                    val defaultCategories = TaskCategory.entries.map {
                        Category(name = it.category)
                    }
                    defaultCategories.forEach { categoryDao.insertCategory(it) }
                }
            }
        }
    }
}