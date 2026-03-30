package com.android.memosnap.feature.note.data.model

import com.android.memosnap.feature.note.domain.model.NoteTag

fun NoteTagEntity.toDomain(): NoteTag {
    return NoteTag(
        name = name,
        id = id
    )
}

fun NoteTag.toEntity(): NoteTagEntity {
    return NoteTagEntity(
        name = name,
        id = id
    )
}

