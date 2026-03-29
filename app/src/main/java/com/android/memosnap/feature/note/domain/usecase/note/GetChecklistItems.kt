package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class GetChecklistItems(
    private val repository: NoteRepository
) {
    operator fun invoke(noteId: Int) = repository.getChecklistItemsByNoteId(noteId)
}

