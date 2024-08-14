package com.android.memosnap.feature.note.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.memosnap.feature.note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}