package com.android.memosnap.feature.note.data.model

import com.android.memosnap.feature.note.domain.model.Category

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name
    )
}

