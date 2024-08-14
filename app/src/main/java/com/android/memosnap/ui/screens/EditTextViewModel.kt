package com.android.memosnap.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EditTextViewModel : ViewModel() {
    private val _noteState = mutableStateOf(EditTextState())
    val noteState: State<EditTextState> = _noteState

    private val _uiState = mutableStateOf(EditTextUIState())
    val uiState: State<EditTextUIState> = _uiState

    fun onEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.ChangeColor -> changeColor(event.color)
            is EditNoteEvent.EnteredBody -> enterBody(event.body)
            is EditNoteEvent.EnteredTitle -> enterTitle(event.title)
            is EditNoteEvent.SaveNote -> saveNote()
            is EditNoteEvent.ChangeBottomSheetVisibility -> changeBottomSheetVisibility(event.isVisible)
        }
    }

    private fun changeColor(color: Int) {
        _noteState.value = _noteState.value.copy(color = color)
    }

    private fun saveNote() {

    }

    private fun changeBottomSheetVisibility(isVisible: Boolean) {
        _uiState.value = _uiState.value.copy(isBottomSheetOpen = isVisible)
    }

    private fun enterTitle(title: String) {
        _noteState.value = _noteState.value.copy(title = title)
    }

    private fun enterBody(body: String) {
        _noteState.value = _noteState.value.copy(body = body)
    }
}