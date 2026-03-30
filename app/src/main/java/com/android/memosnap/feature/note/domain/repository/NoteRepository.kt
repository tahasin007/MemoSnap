package com.android.memosnap.feature.note.domain.repository

import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.ChecklistProgress
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagLink
import com.android.memosnap.feature.note.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun getFilteredNotes(
        categoryId: Int?,
        noteType: NoteType?,
        isCompleted: Boolean?,
        tagId: Int?,
        pinnedOnly: Boolean
    ): Flow<List<Note>>

    fun getTagLinksForNotes(noteIds: List<Int>): Flow<List<NoteTagLink>>

    fun getChecklistProgressForNotes(noteIds: List<Int>): Flow<List<ChecklistProgress>>

    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note?

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNote(note: Note)

    fun getTagsByNoteId(noteId: Int): Flow<List<NoteTag>>

    suspend fun addTagToNote(noteId: Int, tagId: Int)

    suspend fun removeTagFromNote(noteId: Int, tagId: Int)

    fun getChecklistItemsByNoteId(noteId: Int): Flow<List<ChecklistItem>>

    suspend fun insertChecklistItem(item: ChecklistItem): Long

    suspend fun deleteChecklistItem(item: ChecklistItem)

    suspend fun toggleNoteCompletion(noteId: Int, isCompleted: Boolean, completedAt: Long?)

    suspend fun setNoteCategory(noteId: Int, categoryId: Int?)

    suspend fun updateNotePriority(noteId: Int, priority: NotePriority)

    suspend fun setNoteDueDate(noteId: Int, dueAt: Long?)

    suspend fun setNoteType(noteId: Int, noteType: NoteType)
}