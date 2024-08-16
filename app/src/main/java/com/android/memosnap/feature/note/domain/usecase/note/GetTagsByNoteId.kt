package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetTagsByNoteId(
    private val repository: NoteRepository
) {
    operator fun invoke(tagId: Int): Flow<List<NoteTag>> {
        return repository.getTagsByNoteId(tagId)
    }
}