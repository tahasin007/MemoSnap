package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class RemoveTagFromNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int, tagId: Int) {
        repository.removeTagFromNote(noteId, tagId)
    }
}