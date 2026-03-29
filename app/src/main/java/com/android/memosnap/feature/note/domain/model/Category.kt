package com.android.memosnap.feature.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val id: Int? = null,
    val name: String
)