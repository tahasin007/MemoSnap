package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import com.android.memosnap.feature.dailytask.domain.model.Category

data class CategoryState(
    val categories: List<Category> = emptyList()
)