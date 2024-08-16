package com.android.memosnap.feature.note.presentation.notesbytag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesByTagViewModel @Inject constructor(
    private val noteUseCases: NoteTagUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesByTagJob: Job? = null

    init {
        savedStateHandle.get<Int>("tagId")?.let { tagId ->
            if (tagId != -1) {
                getNotesByTag(tagId)
            }
        }
    }

    private fun getNotesByTag(tagId: Int) {
        getNotesByTagJob?.cancel()
        getNotesByTagJob = noteUseCases.getNotesByTag(tagId).onEach { notes ->
            val nonArchivedNotes = notes.filter { it.isArchived }
            _state.value = state.value.copy(
                notes = nonArchivedNotes
            )
        }.launchIn(viewModelScope)
    }
}