package com.android.memosnap.feature.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteTag(
    val name: String,
    @PrimaryKey val id: Int? = null
)