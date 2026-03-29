package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class SetNoteDueDate(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, dueAt: Long?) {
        repository.setNoteDueDate(noteId, dueAt)
    }
}

