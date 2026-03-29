package com.android.memosnap.feature.note.data.repository

import com.android.memosnap.feature.note.data.source.ChecklistItemDao
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef
import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val checklistItemDao: ChecklistItemDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override fun getNotesByCategory(categoryId: Int): Flow<List<Note>> =
        dao.getNotesByCategory(categoryId)

    override suspend fun getNoteById(noteId: Int): Note? = dao.getNoteById(noteId)

    override suspend fun insertNote(note: Note): Long = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun getTagsByNoteId(noteId: Int): Flow<List<NoteTag>> =
        dao.getTagsByNoteId(noteId)

    override suspend fun addTagToNote(noteId: Int, tagId: Int) {
        dao.insertNoteTagCrossRef(NoteTagCrossRef(noteId = noteId, tagId = tagId))
    }

    override suspend fun removeTagFromNote(noteId: Int, tagId: Int) {
        dao.removeTagFromNote(noteId, tagId)
    }

    override fun getChecklistItemsByNoteId(noteId: Int): Flow<List<ChecklistItem>> =
        checklistItemDao.getItemsByNoteId(noteId)

    override suspend fun insertChecklistItem(item: ChecklistItem): Long =
        checklistItemDao.insertItem(item)

    override suspend fun deleteChecklistItem(item: ChecklistItem) {
        checklistItemDao.deleteItem(item)
    }

    override suspend fun toggleNoteCompletion(noteId: Int, isCompleted: Boolean, completedAt: Long?) {
        dao.updateNoteCompletion(noteId, isCompleted, completedAt)
    }

    override suspend fun setNoteCategory(noteId: Int, categoryId: Int?) {
        dao.updateNoteCategory(noteId, categoryId)
    }

    override suspend fun updateNotePriority(noteId: Int, priority: NotePriority) {
        dao.updateNotePriority(noteId, priority)
    }

    override suspend fun setNoteDueDate(noteId: Int, dueAt: Long?) {
        dao.updateNoteDueDate(noteId, dueAt)
    }

    override suspend fun setNoteType(noteId: Int, noteType: NoteType) {
        dao.updateNoteType(noteId, noteType)
    }
}