package com.android.memosnap.feature.note.data.repository

import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        return dao.getNoteById(noteId)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun getTagsByNoteId(noteId: Int): Flow<List<NoteTag>> {
        return dao.getTagsByNoteId(noteId)
    }

    override suspend fun addTagToNote(noteId: Int, tagId: Int) {
        val crossRef = NoteTagCrossRef(noteId = noteId, tagId = tagId)
        dao.insertNoteTagCrossRef(crossRef)
    }
}