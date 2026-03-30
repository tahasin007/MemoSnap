package com.android.memosnap.feature.note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.usecase.category.CategoryUseCases
import com.android.memosnap.feature.note.domain.usecase.note.NoteUseCases
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.shared.state.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val noteTagUseCases: NoteTagUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesJob: Job? = null
    private var getCategoriesJob: Job? = null
    private var getTagsJob: Job? = null
    private var getTagLinksJob: Job? = null
    private var getChecklistProgressJob: Job? = null
    private var sourceNotes: List<com.android.memosnap.feature.note.domain.model.Note> = emptyList()

    init {
        getCategories()
        getTags()
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.SetSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                updateVisibleNotes()
            }

            is NotesEvent.SetCategoryFilter -> {
                _state.value = _state.value.copy(categoryFilter = event.categoryName)
                getNotes()
            }

            is NotesEvent.SetTagFilter -> {
                _state.value = _state.value.copy(tagFilter = event.tag)
                getNotes()
            }

            is NotesEvent.SetTypeFilter -> {
                _state.value = _state.value.copy(typeFilter = event.type)
                getNotes()
            }

            is NotesEvent.SetCompletionFilter -> {
                _state.value = _state.value.copy(completionFilter = event.isCompleted)
                getNotes()
            }

            is NotesEvent.SetPinnedOnly -> {
                _state.value = _state.value.copy(pinnedOnly = event.pinnedOnly)
                getNotes()
            }

            is NotesEvent.SetSmartSectionsEnabled -> {
                _state.value = _state.value.copy(smartSectionsEnabled = event.enabled)
            }

            is NotesEvent.ToggleCompletion -> {
                viewModelScope.launch {
                    noteUseCases.toggleNoteCompletion(event.noteId, event.isCompleted)
                }
            }

            NotesEvent.ClearFilters -> {
                _state.value = _state.value.copy(
                    categoryFilter = null,
                    tagFilter = null,
                    typeFilter = null,
                    completionFilter = null,
                    pinnedOnly = false,
                    smartSectionsEnabled = false,
                    searchQuery = ""
                )
                getNotes()
            }
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()

        val categoryId = _state.value.categories
            .find { it.name == _state.value.categoryFilter }
            ?.id

        getNotesJob = noteUseCases.getFilteredNotes(
            categoryId = categoryId,
            noteType = _state.value.typeFilter,
            isCompleted = _state.value.completionFilter,
            tagId = _state.value.tagFilter?.id,
            pinnedOnly = _state.value.pinnedOnly
        ).onEach { notes ->
            sourceNotes = notes
            updateVisibleNotes()
        }.launchIn(viewModelScope)
    }

    private fun updateVisibleNotes() {
        val query = _state.value.searchQuery.trim()
        val visibleNotes = if (query.isBlank()) {
            sourceNotes
        } else if (query.startsWith("#")) {
            val tagQuery = query.removePrefix("#").trim()
            if (tagQuery.isBlank()) {
                sourceNotes
            } else {
                sourceNotes.filter { note ->
                    val noteId = note.id ?: return@filter false
                    _state.value.tagsByNoteId[noteId]
                        ?.any { tag -> tag.name.contains(tagQuery, ignoreCase = true) } == true
                }
            }
        } else {
            sourceNotes.filter { note ->
                note.title.contains(query, ignoreCase = true) ||
                        note.content.contains(query, ignoreCase = true)
            }
        }

        val orderedNotes = visibleNotes.sortedByDescending { it.isPinned }

        _state.value = _state.value.copy(notes = orderedNotes)
        observeCardMetadata(orderedNotes)
    }

    private fun observeCardMetadata(notes: List<com.android.memosnap.feature.note.domain.model.Note>) {
        val noteIds = notes.mapNotNull { it.id }

        if (noteIds.isEmpty()) {
            getTagLinksJob?.cancel()
            getChecklistProgressJob?.cancel()
            _state.value = _state.value.copy(
                tagsByNoteId = emptyMap(),
                checklistProgressByNoteId = emptyMap()
            )
            return
        }

        getTagLinksJob?.cancel()
        getTagLinksJob = viewModelScope.launch {
            noteUseCases.getTagLinksForNotes(noteIds).collectLatest { links ->
                val tagsByNoteId = links
                    .groupBy { it.noteId }
                    .mapValues { (_, items) ->
                        items.map { link ->
                            com.android.memosnap.feature.note.domain.model.NoteTag(
                                name = link.tagName,
                                id = link.tagId
                            )
                        }
                    }
                _state.value = _state.value.copy(tagsByNoteId = tagsByNoteId)
            }
        }

        getChecklistProgressJob?.cancel()
        getChecklistProgressJob = viewModelScope.launch {
            noteUseCases.getChecklistProgressForNotes(noteIds).collectLatest { progressList ->
                _state.value = _state.value.copy(
                    checklistProgressByNoteId = progressList.associateBy { it.noteId }
                )
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getAllCategories().onEach { categories ->
            _state.value = _state.value.copy(categories = categories)
            // Re-apply current category-name filter after categories list is loaded/refreshed
            getNotes()
        }.launchIn(viewModelScope)
    }

    private fun getTags() {
        getTagsJob?.cancel()
        getTagsJob = noteTagUseCases.getNoteTags().onEach { tags ->
            _state.value = _state.value.copy(tags = tags)
        }.launchIn(viewModelScope)
    }
}