package com.android.memosnap.feature.note.domain.repository

import com.android.memosnap.feature.note.domain.model.NoteTag
import kotlinx.coroutines.flow.Flow

interface NoteTagRepository {
    fun getNoteTags(): Flow<List<NoteTag>>
    suspend fun insertNoteTag(noteTag: NoteTag)
    suspend fun deleteNoteTag(noteTag: NoteTag)
}