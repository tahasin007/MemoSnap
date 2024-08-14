package com.android.memosnap.feature.note.domain.usecase

import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}