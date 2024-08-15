package com.android.memosnap.feature.note.presentation.archivednotes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.usecase.NoteUseCases
import com.android.memosnap.feature.note.presentation.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArchivedNotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes().onEach { notes ->
            val nonArchivedNotes = notes.filter { it.isArchived }
            _state.value = state.value.copy(
                notes = nonArchivedNotes
            )
        }.launchIn(viewModelScope)
    }
}