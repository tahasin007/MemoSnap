package com.android.memosnap.feature.note.domain.usecase.notetag

data class NoteTagUseCases(
    val getNoteTags: GetNoteTags,
    val deleteNoteTag: DeleteNoteTag,
    val addNoteTag: AddNoteTag
)