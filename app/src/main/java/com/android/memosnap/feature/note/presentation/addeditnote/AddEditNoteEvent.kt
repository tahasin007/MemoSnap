package com.android.memosnap.feature.note.presentation.addeditnote

import com.android.memosnap.feature.note.domain.model.NoteTag

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title: String) : AddEditNoteEvent()
    data class EnteredBody(val body: String) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    data class ChangeBottomSheetVisibility(val isVisible: Boolean) : AddEditNoteEvent()
    data class ChangePinnedStatus(val isPinned: Boolean) : AddEditNoteEvent()
    data class ChangeArchiveStatus(val isArchived: Boolean) : AddEditNoteEvent()
    data object DeleteNote : AddEditNoteEvent()
    data object SaveNote : AddEditNoteEvent()
    data class AddTagToNote(val tags: List<NoteTag>) : AddEditNoteEvent()
}