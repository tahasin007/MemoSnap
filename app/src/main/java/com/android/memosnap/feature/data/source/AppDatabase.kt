package com.android.memosnap.feature.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.memosnap.feature.dailytask.data.Converters
import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.data.source.NoteTagDao
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef

@Database(
    entities = [Note::class, NoteTag::class, NoteTagCrossRef::class, Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val noteTagDao: NoteTagDao
    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}