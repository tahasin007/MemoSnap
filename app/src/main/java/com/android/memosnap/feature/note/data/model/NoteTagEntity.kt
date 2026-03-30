package com.android.memosnap.feature.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notetag")
data class NoteTagEntity(
    val name: String,
    @PrimaryKey val id: Int? = null
)

