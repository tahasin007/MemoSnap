package com.android.memosnap.feature.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val dateCreated: String,
    val isPinned: Boolean,
    val color: Int,
    @PrimaryKey val id: Int? = null
)