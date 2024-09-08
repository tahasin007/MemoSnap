package com.android.memosnap.feature.dailytask.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String
)