package com.android.memosnap.feature.note.domain.usecase.notetag

import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import kotlinx.coroutines.flow.Flow

class GetNotesByTagId(
    private val repository: NoteTagRepository
) {
    operator fun invoke(tagId: Int): Flow<List<Note>> {
        return repository.getNotesByTagId(tagId)
    }
}