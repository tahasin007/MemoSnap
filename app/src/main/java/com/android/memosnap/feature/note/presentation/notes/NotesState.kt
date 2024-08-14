package com.android.memosnap.feature.note.presentation.notes

import com.android.memosnap.feature.note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList()
)