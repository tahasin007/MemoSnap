package com.android.memosnap.feature.note.domain.usecase.note

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
    val getTagsByNoteId: GetTagsByNoteId,
    val addTagToNote: AddTagToNote,
    val removeTagFromNote: RemoveTagFromNote
)