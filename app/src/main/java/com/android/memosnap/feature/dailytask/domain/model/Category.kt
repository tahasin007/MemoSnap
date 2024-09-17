package com.android.memosnap.feature.dailytask.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val id: Int? = null,
    val name: String
)