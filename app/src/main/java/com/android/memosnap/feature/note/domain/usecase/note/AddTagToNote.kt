package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class AddTagToNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, tagId: Int) {
        repository.addTagToNote(noteId, tagId)
    }
}