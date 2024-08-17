package com.android.memosnap.feature.note.domain.repository

import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note?

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNote(note: Note)

    fun getTagsByNoteId(noteId: Int): Flow<List<NoteTag>>

    suspend fun addTagToNote(noteId: Int, tagId: Int)

    suspend fun removeTagFromNote(noteId: Int, tagId: Int)
}