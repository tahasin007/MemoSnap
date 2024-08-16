package com.android.memosnap.feature.note.presentation.notetags

sealed class NoteTagsEvent {
    data class AddNewTag(val tag: String) : NoteTagsEvent()
    data class DeleteTag(val tag: String) : NoteTagsEvent()
}