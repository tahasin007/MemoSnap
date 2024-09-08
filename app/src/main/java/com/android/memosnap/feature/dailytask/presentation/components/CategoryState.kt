package com.android.memosnap.feature.dailytask.presentation.components

import com.android.memosnap.feature.dailytask.domain.model.Category

data class CategoryState(
    val categories: List<Category> = emptyList()
)