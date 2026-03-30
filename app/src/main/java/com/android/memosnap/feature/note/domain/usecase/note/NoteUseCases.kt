package com.android.memosnap.feature.note.domain.usecase.note

data class NoteUseCases(
    val getNotes: GetNotes,
    val getFilteredNotes: GetFilteredNotes,
    val getTagLinksForNotes: GetTagLinksForNotes,
    val getChecklistProgressForNotes: GetChecklistProgressForNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
    val getNotesByCategory: GetNotesByCategory,
    val getTagsByNoteId: GetTagsByNoteId,
    val addTagToNote: AddTagToNote,
    val removeTagFromNote: RemoveTagFromNote,
    val getChecklistItems: GetChecklistItems,
    val insertChecklistItem: InsertChecklistItem,
    val deleteChecklistItem: DeleteChecklistItem,
    val toggleNoteCompletion: ToggleNoteCompletion,
    val updateNotePriority: UpdateNotePriority,
    val setNoteDueDate: SetNoteDueDate,
    val setNoteCategory: SetNoteCategory,
    val convertNoteType: ConvertNoteType
)