package com.android.memosnap.feature.note.data.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["noteId", "tagId"],
    indices = [Index(value = ["tagId"])]
)
data class NoteTagCrossRef(
    val noteId: Int,
    val tagId: Int
)

