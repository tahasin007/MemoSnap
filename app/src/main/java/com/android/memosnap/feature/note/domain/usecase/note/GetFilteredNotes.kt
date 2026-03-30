package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class GetFilteredNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        categoryId: Int?,
        noteType: NoteType?,
        isCompleted: Boolean?,
        tagId: Int?,
        pinnedOnly: Boolean
    ) = repository.getFilteredNotes(categoryId, noteType, isCompleted, tagId, pinnedOnly)
}

