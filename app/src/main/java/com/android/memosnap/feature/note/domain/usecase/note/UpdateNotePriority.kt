package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class UpdateNotePriority(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, priority: NotePriority) {
        repository.updateNotePriority(noteId, priority)
    }
}

