package com.android.memosnap.feature.note.data.repository

import com.android.memosnap.feature.note.data.source.NoteTagDao
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import kotlinx.coroutines.flow.Flow

class NoteTagRepositoryImpl(
    private val dao: NoteTagDao
) : NoteTagRepository {
    override fun getNoteTags(): Flow<List<NoteTag>> {
        return dao.getNoteTags()
    }

    override suspend fun insertNoteTag(noteTag: NoteTag) {
        dao.insertNoteTag(noteTag)
    }

    override suspend fun deleteNoteTag(noteTag: NoteTag) {
        dao.deleteNoteTag(noteTag)
    }

    override fun getNotesByTagId(tagId: Int): Flow<List<Note>> {
        return dao.getNotesByTag(tagId)
    }

    override suspend fun getTagById(tagId: Int): NoteTag {
        return dao.getTagById(tagId)
    }
}