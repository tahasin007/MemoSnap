package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import com.android.memosnap.feature.note.domain.model.Category

data class CategoryState(
    val categories: List<Category> = emptyList()
)