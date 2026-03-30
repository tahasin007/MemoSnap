package com.android.memosnap.feature.note.presentation.notes

import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteType

sealed class NotesEvent {
    data class SetSearchQuery(val query: String) : NotesEvent()
    data class SetCategoryFilter(val categoryName: String?) : NotesEvent()
    data class SetTagFilter(val tag: NoteTag?) : NotesEvent()
    data class SetTypeFilter(val type: NoteType?) : NotesEvent()
    data class SetCompletionFilter(val isCompleted: Boolean?) : NotesEvent()
    data class SetPinnedOnly(val pinnedOnly: Boolean) : NotesEvent()
    data class SetSmartSectionsEnabled(val enabled: Boolean) : NotesEvent()
    data class ToggleCompletion(val noteId: Int, val isCompleted: Boolean) : NotesEvent()
    data object ClearFilters : NotesEvent()
}

