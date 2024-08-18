package com.android.memosnap.feature.note.presentation.tags

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.NoteTagsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteTagsViewModel @Inject constructor(
    private val noteUseCases: NoteTagUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(NoteTagsState())
    val state: State<NoteTagsState> = _state

    private val _uiState = mutableStateOf(NoteTagsUIState())
    val uiState: State<NoteTagsUIState> = _uiState

    private var getNotesJob: Job? = null

    init {
        val showAddTagPopup = savedStateHandle.get<Boolean>("showAddTagPopup")
        if (showAddTagPopup == true) {
            _uiState.value = _uiState.value.copy(
                isAddTagPopupVisible = true,
                shouldShowTagListInEditNoteScreen = true
            )
        }
        getNoteTags()
    }

    fun onEvent(event: NoteTagsEvent) {
        when (event) {
            is NoteTagsEvent.AddNewTag -> addNoteTag(event.tag)
            is NoteTagsEvent.DeleteTag -> deleteNoteTag(event.tag)
        }
    }

    private fun addNoteTag(tag: String) {
        viewModelScope.launch {
            noteUseCases.addNoteTag(
                NoteTag(
                    name = tag,
                    id = null
                )
            )
        }
    }

    private fun deleteNoteTag(tag: String) {
        viewModelScope.launch {
            val noteTag = state.value.tags.find { it.name == tag }
            noteTag?.let {
                noteUseCases.deleteNoteTag(it)
            }
        }
    }

    private fun getNoteTags() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNoteTags().onEach { noteTags ->
            _state.value = _state.value.copy(
                tags = noteTags
            )
        }.launchIn(viewModelScope)
    }
}