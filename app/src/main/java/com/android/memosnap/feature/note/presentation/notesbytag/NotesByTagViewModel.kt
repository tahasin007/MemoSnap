package com.android.memosnap.feature.note.presentation.notesbytag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.NoteTagState
import com.android.memosnap.feature.note.presentation.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesByTagViewModel @Inject constructor(
    private val noteUseCases: NoteTagUseCases,
    private val noteTagUseCases: NoteTagUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private val _tagState = mutableStateOf(NoteTagState())
    val tagState: State<NoteTagState> = _tagState

    private var getNotesByTagJob: Job? = null

    init {
        val tagId = savedStateHandle.get<Int>("tagId")

        if (tagId != null && tagId != -1) {
            getNotesByTag(tagId)
            viewModelScope.launch {
                noteTagUseCases.getTagById(tagId).also { tag ->
                    _tagState.value = tagState.value.copy(
                        tagName = tag.name,
                        id = tag.id
                    )
                }
            }
        }
    }

    private fun getNotesByTag(tagId: Int) {
        getNotesByTagJob?.cancel()
        getNotesByTagJob = noteUseCases.getNotesByTag(tagId).onEach { notes ->
            _notesState.value = notesState.value.copy(
                notes = notes
            )
        }.launchIn(viewModelScope)
    }
}