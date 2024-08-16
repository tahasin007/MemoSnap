package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}