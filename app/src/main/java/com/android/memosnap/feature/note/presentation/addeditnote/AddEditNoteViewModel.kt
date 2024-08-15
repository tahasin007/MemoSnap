package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.usecase.NoteUseCases
import com.android.memosnap.utils.NoteUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteState = mutableStateOf(AddEditNoteState())
    val noteState: State<AddEditNoteState> = _noteState

    private val _uiState = mutableStateOf(AddEditNoteUIState())
    val uiState: State<AddEditNoteUIState> = _uiState

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        _noteState.value = _noteState.value.copy(
                            title = note.title,
                            content = note.content,
                            dateCreated = note.dateCreated,
                            color = note.color,
                            isPinned = note.isPinned,
                            id = noteId
                        )
                    }
                }
            }
        }

        if (_noteState.value.dateCreated.isEmpty()) {
            _noteState.value =
                _noteState.value.copy(dateCreated = NoteUtils.getCurrentFormattedDate())
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> changeColor(event.color)
            is AddEditNoteEvent.EnteredBody -> enterBody(event.body)
            is AddEditNoteEvent.EnteredTitle -> enterTitle(event.title)
            is AddEditNoteEvent.SaveNoteAdd -> saveNote()
            is AddEditNoteEvent.ChangeBottomSheetVisibility -> {
                changeBottomSheetVisibility(event.isVisible)
            }

            is AddEditNoteEvent.ChangePinnedStatus -> changePinnedStatus(event.isPinned)
        }
    }

    private fun changePinnedStatus(pinned: Boolean) {
        if (pinned != _noteState.value.isPinned) {
            _noteState.value = _noteState.value.copy(isPinned = pinned)
        }
    }

    private fun changeColor(color: Int) {
        if (color != _noteState.value.color) {
            _noteState.value = _noteState.value.copy(color = color)
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            noteUseCases.addNote(
                Note(
                    title = _noteState.value.title,
                    content = _noteState.value.content,
                    dateCreated = _noteState.value.dateCreated,
                    color = _noteState.value.color,
                    isPinned = _noteState.value.isPinned,
                    id = _noteState.value.id
                )
            )
        }
    }

    private fun changeBottomSheetVisibility(isVisible: Boolean) {
        if (isVisible != _uiState.value.isBottomSheetOpen) {
            _uiState.value = _uiState.value.copy(isBottomSheetOpen = isVisible)
        }
    }

    private fun enterTitle(title: String) {
        if (title != _noteState.value.title) {
            _noteState.value = _noteState.value.copy(title = title)
        }
    }

    private fun enterBody(content: String) {
        if (content != _noteState.value.content) {
            _noteState.value = _noteState.value.copy(content = content)
        }
    }
}