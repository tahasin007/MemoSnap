package com.android.memosnap.feature.note.domain.model

data class ChecklistProgress(
    val noteId: Int,
    val total: Int,
    val completed: Int
)
