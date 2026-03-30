package com.android.memosnap.feature.note.presentation.shared.state

import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.model.ChecklistProgress
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val tags: List<NoteTag> = emptyList(),
    val tagsByNoteId: Map<Int, List<NoteTag>> = emptyMap(),
    val checklistProgressByNoteId: Map<Int, ChecklistProgress> = emptyMap(),
    val categoryFilter: String? = null,
    val tagFilter: NoteTag? = null,
    val typeFilter: NoteType? = null,
    val completionFilter: Boolean? = null,
    val pinnedOnly: Boolean = false,
    val smartSectionsEnabled: Boolean = false,
    val searchQuery: String = ""
)
