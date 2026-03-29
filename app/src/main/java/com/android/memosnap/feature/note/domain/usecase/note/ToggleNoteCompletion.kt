package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class ToggleNoteCompletion(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, isCompleted: Boolean) {
        val completedAt = if (isCompleted) System.currentTimeMillis() else null
        repository.toggleNoteCompletion(noteId, isCompleted, completedAt)
    }
}

