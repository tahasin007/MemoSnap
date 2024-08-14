package com.android.memosnap.ui.screens

sealed class EditNoteEvent {
    data class EnteredTitle(val title: String) : EditNoteEvent()
    data class EnteredBody(val body: String) : EditNoteEvent()
    data class ChangeColor(val color: Int) : EditNoteEvent()
    data class ChangeBottomSheetVisibility(val isVisible: Boolean) : EditNoteEvent()
    data object SaveNote : EditNoteEvent()
}