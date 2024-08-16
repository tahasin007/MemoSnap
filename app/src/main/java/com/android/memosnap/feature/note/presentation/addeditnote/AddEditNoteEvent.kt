package com.android.memosnap.feature.note.presentation.addeditnote

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title: String) : AddEditNoteEvent()
    data class EnteredBody(val body: String) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    data class ChangeBottomSheetVisibility(val isVisible: Boolean) : AddEditNoteEvent()
    data class ChangePinnedStatus(val isPinned: Boolean) : AddEditNoteEvent()
    data class ChangeArchiveStatus(val isArchived: Boolean) : AddEditNoteEvent()
    data object DeleteNote : AddEditNoteEvent()
    data object SaveNote : AddEditNoteEvent()
    data class AddTagToNote(val noteId: Int?, val tagIds: List<Int>) : AddEditNoteEvent()
}