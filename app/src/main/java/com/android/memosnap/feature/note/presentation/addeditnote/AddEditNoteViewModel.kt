package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.domain.usecase.category.CategoryUseCases
import com.android.memosnap.feature.note.domain.usecase.note.NoteUseCases
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
import com.android.memosnap.feature.note.presentation.shared.state.NoteTagsState
import com.android.memosnap.feature.note.util.NoteUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
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

    private val _categories = mutableStateOf<List<Category>>(emptyList())
    val categories: State<List<Category>> = _categories

    private var originalNote: Note? = null
    private var originalChecklistItems: List<ChecklistItem> = emptyList()
    private var originalTagsByNoteId = listOf<NoteTag>()

    private var getNoteTagsJob: Job? = null
    private var getTagsByNoteIdJob: Job? = null
    private var getChecklistItemsJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        val noteId = savedStateHandle.get<Int>("noteId") ?: -1
        val noteTypeArg = savedStateHandle.get<String>("noteType") ?: NoteType.REGULAR.name
        val createModeArg = savedStateHandle.get<String>("createMode") ?: "NOTE"
        setNoteState(noteId)

        if (noteId == -1) {
            val parsedType = when (createModeArg.uppercase()) {
                "CHECKLIST" -> NoteType.CHECKLIST
                else -> runCatching { NoteType.valueOf(noteTypeArg) }.getOrDefault(NoteType.REGULAR)
            }
            _noteState.value = _noteState.value.copy(noteType = parsedType)

            if (createModeArg.equals("PHOTO", ignoreCase = true)) {
                _uiState.value = _uiState.value.copy(openImagePickerOnStart = true)
            }
        }

        getNoteTags()
        getCategories()
        getTagsByNoteId(noteId)
        getChecklistItemsByNoteId(noteId)
    }


    fun consumeOpenImagePickerRequest() {
        if (_uiState.value.openImagePickerOnStart) {
            _uiState.value = _uiState.value.copy(openImagePickerOnStart = false)
        }
    }

    fun consumeCloseScreenRequest() {
        if (_uiState.value.shouldCloseScreen) {
            _uiState.value = _uiState.value.copy(shouldCloseScreen = false)
        }
    }

    fun consumeUserMessage() {
        if (_uiState.value.userMessage != null) {
            _uiState.value = _uiState.value.copy(userMessage = null)
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> changeColor(event.color)
            is AddEditNoteEvent.EnteredBody -> enterBody(event.body)
            is AddEditNoteEvent.EnteredTitle -> enterTitle(event.title)
            is AddEditNoteEvent.SaveNote -> saveNote()
            is AddEditNoteEvent.ChangeBottomSheetVisibility -> changeBottomSheetVisibility(event.isVisible)
            is AddEditNoteEvent.ChangePinnedStatus -> changePinnedStatus(event.isPinned)
            is AddEditNoteEvent.ChangeArchiveStatus -> changeArchiveStatus(event.isArchived)
            is AddEditNoteEvent.SelectImage -> selectImage(event.imageData)
            is AddEditNoteEvent.DeleteNote -> deleteNote()
            is AddEditNoteEvent.AddTagToNote -> addTagToNote(event.tags)
            is AddEditNoteEvent.CreateTag -> createTag(event.name)
            is AddEditNoteEvent.DeleteTag -> deleteTag(event.tag)
            is AddEditNoteEvent.AddChecklistItem -> addChecklistItem(event.text)
            is AddEditNoteEvent.EditChecklistItem -> editChecklistItem(event.index, event.item)
            is AddEditNoteEvent.RemoveChecklistItem -> removeChecklistItem(event.index)
            is AddEditNoteEvent.MoveChecklistItem -> moveChecklistItem(
                event.fromIndex,
                event.toIndex
            )

            is AddEditNoteEvent.SetCategory -> setCategory(event.categoryId)
            is AddEditNoteEvent.SetPriority -> setPriority(event.priority)
            is AddEditNoteEvent.SetDueDate -> setDueDate(event.dueAt)
            is AddEditNoteEvent.SetReminder -> setReminder(event.reminderAt)
            is AddEditNoteEvent.ConvertNoteType -> convertNoteType(event.noteType)
        }
    }

    // Check if the note has been edited
    fun isNoteEdited(): Boolean {
        return originalNote?.let {
            val currentTags = _tagsByNoteId.value.tags
            val originalTags = originalTagsByNoteId
            val currentChecklist = _noteState.value.checklistItems

            val tagsEdited = currentTags.size != originalTags.size ||
                    !currentTags.containsAll(originalTags) ||
                    !originalTags.containsAll(currentTags)

            val checklistEdited = currentChecklist != originalChecklistItems

            val imageEdited = when {
                it.imageData == null && _noteState.value.imageData == null -> false
                it.imageData == null || _noteState.value.imageData == null -> true
                else -> !it.imageData.contentEquals(_noteState.value.imageData)
            }

            it.title != _noteState.value.title ||
                    it.content != _noteState.value.content ||
                    it.color != _noteState.value.color ||
                    it.isPinned != _noteState.value.isPinned ||
                    it.isArchived != _noteState.value.isArchived ||
                    it.categoryId != _noteState.value.categoryId ||
                    it.priority != _noteState.value.priority ||
                    it.dueAt != _noteState.value.dueAt ||
                    it.reminderAt != _noteState.value.reminderAt ||
                    it.noteType != _noteState.value.noteType ||
                    imageEdited ||
                    tagsEdited ||
                    checklistEdited
        } ?: _noteState.value.title.isNotBlank() && _noteState.value.content.isNotBlank()
    }

    private fun addTagToNote(tags: List<NoteTag>) {
        _tagsByNoteId.value = _tagsByNoteId.value.copy(tags = tags)
    }

    private fun createTag(name: String) {
        val normalized = name.trim()
        if (normalized.isBlank()) return

        val exists = _tagsState.value.tags.any { it.name.equals(normalized, ignoreCase = true) }
        if (exists) return

        viewModelScope.launch {
            noteTagUseCases.addNoteTag(NoteTag(name = normalized))
        }
    }

    private fun deleteTag(tag: NoteTag) {
        viewModelScope.launch {
            noteTagUseCases.deleteNoteTag(tag)
            _tagsByNoteId.value = _tagsByNoteId.value.copy(
                tags = _tagsByNoteId.value.tags.filterNot { selected -> selected.id == tag.id }
            )
        }
    }

    private fun changeColor(color: Int) {
        if (color != _noteState.value.color) {
            _noteState.value = _noteState.value.copy(color = color)
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            try {
                val note = Note(
                    title = _noteState.value.title,
                    content = _noteState.value.content,
                    dateCreated = _noteState.value.dateCreated,
                    color = _noteState.value.color,
                    isPinned = _noteState.value.isPinned,
                    isArchived = _noteState.value.isArchived,
                    imageData = _noteState.value.imageData,
                    categoryId = _noteState.value.categoryId,
                    priority = _noteState.value.priority,
                    isCompleted = _noteState.value.isCompleted,
                    completedAt = _noteState.value.completedAt,
                    dueAt = _noteState.value.dueAt,
                    reminderAt = _noteState.value.reminderAt,
                    noteType = _noteState.value.noteType,
                    id = _noteState.value.id
                )

                val savedNoteId = noteUseCases.addNote(note).toInt()
                val noteId = _noteState.value.id ?: savedNoteId

                // Save checklist items (replace all existing items for this note)
                val existingItems = noteUseCases.getChecklistItems(noteId).first()
                existingItems.forEach { item ->
                    noteUseCases.deleteChecklistItem(item)
                }
                if (_noteState.value.noteType == NoteType.CHECKLIST) {
                    _noteState.value.checklistItems.forEachIndexed { index, item ->
                        noteUseCases.insertChecklistItem(
                            item.copy(
                                noteId = noteId,
                                sortOrder = index
                            )
                        )
                    }
                }

                // Save tags delta
                val currentTags = _tagsByNoteId.value.tags
                val originalTags = originalTagsByNoteId

                val addedTags = currentTags.filter { it !in originalTags }
                addedTags.forEach { tag ->
                    noteUseCases.addTagToNote(noteId, tag.id!!)
                }

                val removedTags = originalTags.filter { it !in currentTags }
                removedTags.forEach { tag ->
                    noteUseCases.removeTagFromNote(noteId, tag.id!!)
                }
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    shouldCloseScreen = true
                )
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    userMessage = "Could not save note. Please try again."
                )
            }
        }
    }

    private fun changeBottomSheetVisibility(isVisible: Boolean) {
        if (isVisible != _uiState.value.isBottomSheetOpen) {
            _uiState.value = _uiState.value.copy(isBottomSheetOpen = isVisible)
        }
    }

    private fun selectImage(imageData: ByteArray?) {
        val isDifferent = when {
            imageData == null && _noteState.value.imageData == null -> false
            imageData == null || _noteState.value.imageData == null -> true
            else -> !imageData.contentEquals(_noteState.value.imageData)
        }

        if (isDifferent) {
            _noteState.value = _noteState.value.copy(imageData = imageData)
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

    private fun addChecklistItem(text: String) {
        val items = _noteState.value.checklistItems.toMutableList()
        items.add(
            ChecklistItem(
                noteId = _noteState.value.id ?: 0,
                text = text,
                sortOrder = items.size
            )
        )
        _noteState.value = _noteState.value.copy(checklistItems = normalizeChecklistItems(items))
    }

    private fun editChecklistItem(index: Int, item: ChecklistItem) {
        val items = _noteState.value.checklistItems.toMutableList()
        if (index in items.indices) {
            items[index] = item
            _noteState.value =
                _noteState.value.copy(checklistItems = normalizeChecklistItems(items))
        }
    }

    private fun removeChecklistItem(index: Int) {
        val items = _noteState.value.checklistItems.toMutableList()
        if (index in items.indices) {
            items.removeAt(index)
            _noteState.value =
                _noteState.value.copy(checklistItems = normalizeChecklistItems(items))
        }
    }

    private fun moveChecklistItem(fromIndex: Int, toIndex: Int) {
        val items = _noteState.value.checklistItems.toMutableList()
        if (fromIndex !in items.indices || toIndex !in items.indices || fromIndex == toIndex) {
            return
        }
        val moved = items.removeAt(fromIndex)
        items.add(toIndex, moved)
        _noteState.value = _noteState.value.copy(checklistItems = normalizeChecklistItems(items))
    }

    private fun setCategory(categoryId: Int?) {
        if (_noteState.value.categoryId != categoryId) {
            _noteState.value = _noteState.value.copy(categoryId = categoryId)
        }
    }

    private fun setPriority(priority: NotePriority) {
        if (_noteState.value.priority != priority) {
            _noteState.value = _noteState.value.copy(priority = priority)
        }
    }

    private fun setDueDate(dueAt: Long?) {
        if (_noteState.value.dueAt != dueAt) {
            _noteState.value = _noteState.value.copy(dueAt = dueAt)
        }
    }

    private fun setReminder(reminderAt: Long?) {
        if (_noteState.value.reminderAt != reminderAt) {
            _noteState.value = _noteState.value.copy(reminderAt = reminderAt)
        }
    }

    private fun convertNoteType(noteType: NoteType) {
        if (_noteState.value.noteType != noteType) {
            when (noteType) {
                NoteType.CHECKLIST -> {
                    val lines = _noteState.value.content
                        .lineSequence()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .toList()

                    val checklistItems = lines.mapIndexed { index, line ->
                        ChecklistItem(
                            noteId = _noteState.value.id ?: 0,
                            text = line,
                            sortOrder = index
                        )
                    }

                    _noteState.value = _noteState.value.copy(
                        noteType = NoteType.CHECKLIST,
                        content = "",
                        checklistItems = normalizeChecklistItems(checklistItems)
                    )
                }

                NoteType.REGULAR -> {
                    val mergedContent = _noteState.value.checklistItems
                        .joinToString(separator = "\n") { it.text.trim() }
                        .trimEnd()

                    _noteState.value = _noteState.value.copy(
                        noteType = NoteType.REGULAR,
                        content = mergedContent,
                        checklistItems = emptyList()
                    )
                }
            }
        }
    }

    private fun normalizeChecklistItems(items: List<ChecklistItem>): List<ChecklistItem> {
        return items.mapIndexed { index, checklistItem ->
            checklistItem.copy(sortOrder = index)
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
                        imageData = _noteState.value.imageData,
                        categoryId = _noteState.value.categoryId,
                        priority = _noteState.value.priority,
                        isCompleted = _noteState.value.isCompleted,
                        completedAt = _noteState.value.completedAt,
                        dueAt = _noteState.value.dueAt,
                        reminderAt = _noteState.value.reminderAt,
                        noteType = _noteState.value.noteType,
                        id = _noteState.value.id
                    )
                )
            }
        }
    }

    private fun setNoteState(noteId: Int?) {
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
                        isArchived = note.isArchived,
                        imageData = note.imageData,
                        categoryId = note.categoryId,
                        priority = note.priority,
                        isCompleted = note.isCompleted,
                        completedAt = note.completedAt,
                        dueAt = note.dueAt,
                        reminderAt = note.reminderAt,
                        noteType = note.noteType,
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
            _tagsState.value = _tagsState.value.copy(tags = noteTags)
        }.launchIn(viewModelScope)
    }

    private fun getTagsByNoteId(noteId: Int?) {
        if (noteId == null || noteId == -1) return
        getTagsByNoteIdJob?.cancel()
        getTagsByNoteIdJob = noteUseCases.getTagsByNoteId(noteId).onEach {
            _tagsByNoteId.value = _tagsByNoteId.value.copy(tags = it)
            originalTagsByNoteId = it
        }.launchIn(viewModelScope)
    }

    private fun getChecklistItemsByNoteId(noteId: Int?) {
        if (noteId == null || noteId == -1) return
        getChecklistItemsJob?.cancel()
        getChecklistItemsJob = noteUseCases.getChecklistItems(noteId).onEach { items ->
            val normalized = normalizeChecklistItems(items)
            _noteState.value = _noteState.value.copy(checklistItems = normalized)
            originalChecklistItems = normalized
        }.launchIn(viewModelScope)
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getAllCategories().onEach { loaded ->
            _categories.value = loaded
        }.launchIn(viewModelScope)
    }
}