package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.repository.NoteRepository

class InsertChecklistItem(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(item: ChecklistItem): Long {
        return repository.insertChecklistItem(item)
    }
}

