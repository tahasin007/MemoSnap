package com.android.memosnap.feature.note.data.repository

import com.android.memosnap.feature.note.data.model.toDomain
import com.android.memosnap.feature.note.data.model.toEntity
import com.android.memosnap.feature.note.data.source.NoteTagDao
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteTagRepositoryImpl(
    private val dao: NoteTagDao
) : NoteTagRepository {
    override fun getNoteTags(): Flow<List<NoteTag>> {
        return dao.getNoteTags().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertNoteTag(noteTag: NoteTag) {
        dao.insertNoteTag(noteTag.toEntity())
    }

    override suspend fun deleteNoteTag(noteTag: NoteTag) {
        val tagId = noteTag.id ?: return
        dao.deleteTagAndLinks(tagId)
    }

    override fun getNotesByTagId(tagId: Int): Flow<List<Note>> {
        return dao.getNotesByTag(tagId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getTagById(tagId: Int): NoteTag {
        return dao.getTagById(tagId).toDomain()
    }
}