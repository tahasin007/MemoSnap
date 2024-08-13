package com.android.memosnap.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EditTextViewModel : ViewModel() {
    private val _state = mutableStateOf(EditTextState())
    val state: State<EditTextState> = _state

    fun onEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.ChangeColor -> TODO()
            is EditNoteEvent.EnteredBody -> enterBody(event.body)
            is EditNoteEvent.EnteredTitle -> enterTitle(event.title)
            is EditNoteEvent.SaveNote -> TODO()
        }
    }

    private fun enterTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun enterBody(body: String) {
        _state.value = _state.value.copy(body = body)
    }
}