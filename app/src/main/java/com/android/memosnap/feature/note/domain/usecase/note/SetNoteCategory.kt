package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class SetNoteCategory(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, categoryId: Int?) {
        repository.setNoteCategory(noteId, categoryId)
    }
}

