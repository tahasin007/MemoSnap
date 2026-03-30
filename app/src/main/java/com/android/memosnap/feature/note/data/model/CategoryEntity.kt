package com.android.memosnap.feature.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: Int? = null,
    val name: String
)

