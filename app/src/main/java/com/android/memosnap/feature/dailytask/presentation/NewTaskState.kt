package com.android.memosnap.feature.dailytask.presentation

import com.android.memosnap.feature.dailytask.domain.model.SubTask

data class NewTaskState(
    val taskName: String = "",
    val subTasks: MutableList<SubTask> = mutableListOf(),
    val selectedCategory: String = "No Category",
    val categories: List<String> = mutableListOf(),
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.NONE
)