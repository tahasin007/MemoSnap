package com.android.memosnap.feature.note.domain.usecase.notetag

import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository

class GetNoteTag(
    private val repository: NoteTagRepository
) {
    suspend operator fun invoke(tagId: Int): NoteTag {
        return repository.getTagById(tagId)
    }
}