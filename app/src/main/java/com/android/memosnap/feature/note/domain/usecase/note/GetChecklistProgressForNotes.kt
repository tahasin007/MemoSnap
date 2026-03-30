package com.android.memosnap.feature.note.domain.usecase.note

import com.android.memosnap.feature.note.domain.repository.NoteRepository

class GetChecklistProgressForNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(noteIds: List<Int>) = repository.getChecklistProgressForNotes(noteIds)
}
