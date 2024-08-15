package com.android.memosnap.feature.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val dateCreated: String,
    val color: Int,
    val isPinned: Boolean,
    val isArchived: Boolean,
    @PrimaryKey val id: Int? = null
)