package com.android.memosnap.feature.dailytask.presentation

data class NewTaskState(
    val taskName: String = "",
    val subTasks: MutableList<SubTask> = mutableListOf(),
    val selectedCategory: String = "No Category",
    val categories: List<String> = mutableListOf(),
    val priority: TaskPriority = TaskPriority.NONE
)