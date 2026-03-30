package com.android.memosnap.feature.note.domain.model

data class ChecklistItem(
    val noteId: Int,
    val text: String,
    val isCompleted: Boolean = false,
    val sortOrder: Int = 0,
    val id: Int = 0
)