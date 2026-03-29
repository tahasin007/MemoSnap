package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class GetNotesByCategory(
    private val repository: NoteRepository
) {
    operator fun invoke(categoryId: Int) = repository.getNotesByCategory(categoryId)
}

