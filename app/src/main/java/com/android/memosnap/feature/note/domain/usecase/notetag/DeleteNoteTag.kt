package com.android.memosnap.feature.note.domain.usecase.notetag

import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository

class DeleteNoteTag(
    private val repository: NoteTagRepository
) {
    suspend operator fun invoke(note: NoteTag) {
        repository.deleteNoteTag(note)
    }
}