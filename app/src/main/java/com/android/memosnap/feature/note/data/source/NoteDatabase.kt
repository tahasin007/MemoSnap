package com.android.memosnap.feature.note.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef

@Database(
    entities = [Note::class, NoteTag::class, NoteTagCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val noteTagDao: NoteTagDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}