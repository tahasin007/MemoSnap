package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.usecase.note.NoteUseCases
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.NoteTagsState
import com.android.memosnap.utils.NoteUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val noteTagUseCases: NoteTagUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteState = mutableStateOf(AddEditNoteState())
    val noteState: State<AddEditNoteState> = _noteState

    private val _tagsState = mutableStateOf(NoteTagsState())
    val tagsState: State<NoteTagsState> = _tagsState

    private val _tagsByNoteId = mutableStateOf(NoteTagsState())
    val tagsByNoteId: State<NoteTagsState> = _tagsByNoteId

    private val _uiState = mutableStateOf(AddEditNoteUIState())
    val uiState: State<AddEditNoteUIState> = _uiState

    private var originalNote: Note? = null
    private var _originalTagsByNoteId = listOf<NoteTag>()

    private var getNoteTagsJob: Job? = null
    private var getTagsByNoteIdJob: Job? = null

    init {
        val noteId = savedStateHandle.get<Int>("noteId")
        setNoteState(noteId)
        getNoteTags()
        getTagsByNoteId(noteId)
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> changeColor(event.color)
            is AddEditNoteEvent.EnteredBody -> enterBody(event.body)
            is AddEditNoteEvent.EnteredTitle -> enterTitle(event.title)
            is AddEditNoteEvent.SaveNote -> saveNote()
            is AddEditNoteEvent.ChangeBottomSheetVisibility -> {
                changeBottomSheetVisibility(event.isVisible)
            }

            is AddEditNoteEvent.ChangePinnedStatus -> changePinnedStatus(event.isPinned)
            is AddEditNoteEvent.ChangeArchiveStatus -> changeArchiveStatus(event.isArchived)
            is AddEditNoteEvent.DeleteNote -> deleteNote()
            is AddEditNoteEvent.AddTagToNote -> addTagToNote(event.tags)
        }
    }

    // Check if the note has been edited
    fun isNoteEdited(): Boolean {
        return originalNote?.let {
            val currentTags = _tagsByNoteId.value.tags
            val originalTags = _originalTagsByNoteId

            val tagsEdited = currentTags.size != originalTags.size ||
                    !currentTags.containsAll(originalTags) ||
                    !originalTags.containsAll(currentTags)

            it.title != _noteState.value.title ||
                    it.content != _noteState.value.content ||
                    it.color != _noteState.value.color ||
                    it.isPinned != _noteState.value.isPinned ||
                    it.isArchived != _noteState.value.isArchived ||
                    tagsEdited
        } ?: _noteState.value.title.isNotBlank() && _noteState.value.content.isNotBlank()
    }

    // Adding tags to a note
    private fun addTagToNote(tags: List<NoteTag>) {
        _tagsByNoteId.value = _tagsByNoteId.value.copy(tags = tags)
    }

    private fun changeColor(color: Int) {
        if (color != _noteState.value.color) {
            _noteState.value = _noteState.value.copy(color = color)
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                val note = Note(
                    title = _noteState.value.title,
                    content = _noteState.value.content,
                    dateCreated = _noteState.value.dateCreated,
                    color = _noteState.value.color,
                    isPinned = _noteState.value.isPinned,
                    isArchived = _noteState.value.isArchived,
                    id = _noteState.value.id
                )

                val savedNoteId = noteUseCases.addNote(note).toInt()

                // Handle added tags
                val noteId = _noteState.value.id ?: savedNoteId
                val currentTags = _tagsByNoteId.value.tags
                val originalTags = _originalTagsByNoteId

                // Find added tags
                val addedTags = currentTags.filter { it !in originalTags }
                addedTags.forEach { tag ->
                    noteUseCases.addTagToNote(noteId, tag.id!!)
                }

                // Find removed tags
                val removedTags = originalTags.filter { it !in currentTags }
                removedTags.forEach { tag ->
                    noteUseCases.removeTagFromNote(noteId, tag.id!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    private fun changePinnedStatus(isPinned: Boolean) {
        if (isPinned != _noteState.value.isPinned) {
            _noteState.value = _noteState.value.copy(isPinned = isPinned)
        }
    }

    private fun changeArchiveStatus(isArchived: Boolean) {
        if (isArchived != _noteState.value.isArchived) {
            _noteState.value = _noteState.value.copy(isArchived = isArchived)
        }
    }

    private fun deleteNote() {
        if (_noteState.value.id != null) {
            viewModelScope.launch {
                noteUseCases.deleteNote(
                    Note(
                        title = _noteState.value.title,
                        content = _noteState.value.content,
                        dateCreated = _noteState.value.dateCreated,
                        color = _noteState.value.color,
                        isPinned = _noteState.value.isPinned,
                        isArchived = _noteState.value.isArchived,
                        id = _noteState.value.id
                    )
                )
            }
        }
    }

    private fun setNoteState(noteId: Int?) {
        // Set the note state when editing an existing note
        if (noteId != null && noteId != -1) {
            viewModelScope.launch {
                noteUseCases.getNote(noteId)?.also { note ->
                    originalNote = note
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

        if (_noteState.value.dateCreated.isEmpty()) {
            _noteState.value = _noteState.value.copy(
                dateCreated = NoteUtils.getCurrentFormattedDate()
            )
        }
    }

    private fun getNoteTags() {
        getNoteTagsJob?.cancel()
        getNoteTagsJob = noteTagUseCases.getNoteTags().onEach { noteTags ->
            _tagsState.value = _tagsState.value.copy(
                tags = noteTags
            )
        }.launchIn(viewModelScope)
    }

    private fun getTagsByNoteId(noteId: Int?) {
        if (noteId == null) return
        getTagsByNoteIdJob?.cancel()
        getTagsByNoteIdJob = noteUseCases.getTagsByNoteId(noteId).onEach {
            _tagsByNoteId.value = _tagsByNoteId.value.copy(
                tags = it
            )
            // Store the original tags for comparison later
            _originalTagsByNoteId = it
        }.launchIn(viewModelScope)
    }
}