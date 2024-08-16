package com.android.memosnap.feature.note.domain.model

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRef(
    val noteId: Int,
    val tagId: Int
)