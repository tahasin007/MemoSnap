package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class ConvertNoteType(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, noteType: NoteType) {
        repository.setNoteType(noteId, noteType)
    }
}

