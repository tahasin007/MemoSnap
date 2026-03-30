package com.android.memosnap.feature.note.data.model

import com.android.memosnap.feature.note.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        title = title,
        content = content,
        dateCreated = dateCreated,
        color = color,
        isPinned = isPinned,
        isArchived = isArchived,
        imageData = imageData,
        categoryId = categoryId,
        priority = priority,
        isCompleted = isCompleted,
        completedAt = completedAt,
        dueAt = dueAt,
        reminderAt = reminderAt,
        noteType = noteType,
        id = id
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        title = title,
        content = content,
        dateCreated = dateCreated,
        color = color,
        isPinned = isPinned,
        isArchived = isArchived,
        imageData = imageData,
        categoryId = categoryId,
        priority = priority,
        isCompleted = isCompleted,
        completedAt = completedAt,
        dueAt = dueAt,
        reminderAt = reminderAt,
        noteType = noteType,
        id = id
    )
}

