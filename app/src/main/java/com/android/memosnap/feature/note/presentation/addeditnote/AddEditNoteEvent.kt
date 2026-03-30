package com.android.memosnap.feature.note.presentation.addeditnote

import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteType

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title: String) : AddEditNoteEvent()
    data class EnteredBody(val body: String) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    data class ChangeBottomSheetVisibility(val isVisible: Boolean) : AddEditNoteEvent()
    data class ChangePinnedStatus(val isPinned: Boolean) : AddEditNoteEvent()
    data class ChangeArchiveStatus(val isArchived: Boolean) : AddEditNoteEvent()
    data class SelectImage(val imageData: ByteArray?) : AddEditNoteEvent()
    data object DeleteNote : AddEditNoteEvent()
    data object SaveNote : AddEditNoteEvent()
    data class AddTagToNote(val tags: List<NoteTag>) : AddEditNoteEvent()
    data class CreateTag(val name: String) : AddEditNoteEvent()
    data class DeleteTag(val tag: NoteTag) : AddEditNoteEvent()

    data class AddChecklistItem(val text: String = "") : AddEditNoteEvent()
    data class EditChecklistItem(val index: Int, val item: ChecklistItem) : AddEditNoteEvent()
    data class RemoveChecklistItem(val index: Int) : AddEditNoteEvent()
    data class MoveChecklistItem(val fromIndex: Int, val toIndex: Int) : AddEditNoteEvent()
    data class SetCategory(val categoryId: Int?) : AddEditNoteEvent()
    data class SetPriority(val priority: NotePriority) : AddEditNoteEvent()
    data class SetDueDate(val dueAt: Long?) : AddEditNoteEvent()
    data class SetReminder(val reminderAt: Long?) : AddEditNoteEvent()
    data class ConvertNoteType(val noteType: NoteType) : AddEditNoteEvent()
}