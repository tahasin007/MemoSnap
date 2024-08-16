package com.android.memosnap.feature.note.domain.usecase.notetag

import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import kotlinx.coroutines.flow.Flow

class GetNoteTags(
    private val repository: NoteTagRepository
) {
    operator fun invoke(): Flow<List<NoteTag>> {
        return repository.getNoteTags()
    }
}