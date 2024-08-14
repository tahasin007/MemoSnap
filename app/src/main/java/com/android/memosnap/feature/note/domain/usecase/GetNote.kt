package com.android.memosnap.feature.note.domain.usecase

import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}