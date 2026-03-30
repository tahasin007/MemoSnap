package com.android.memosnap.feature.note.data.model

import com.android.memosnap.feature.note.domain.model.ChecklistItem

fun ChecklistItemEntity.toDomain(): ChecklistItem {
    return ChecklistItem(
        noteId = noteId,
        text = text,
        isCompleted = isCompleted,
        sortOrder = sortOrder,
        id = id
    )
}

fun ChecklistItem.toEntity(): ChecklistItemEntity {
    return ChecklistItemEntity(
        noteId = noteId,
        text = text,
        isCompleted = isCompleted,
        sortOrder = sortOrder,
        id = id
    )
}

