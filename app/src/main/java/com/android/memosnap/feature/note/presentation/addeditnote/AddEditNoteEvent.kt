package com.android.memosnap.feature.note.presentation.addeditnote

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title: String) : AddEditNoteEvent()
    data class EnteredBody(val body: String) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    data class ChangeBottomSheetVisibility(val isVisible: Boolean) : AddEditNoteEvent()
    data class ChangePinnedStatus(val isPinned: Boolean) : AddEditNoteEvent()
    data object SaveNoteAdd : AddEditNoteEvent()
}