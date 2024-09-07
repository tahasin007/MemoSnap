package com.android.memosnap.feature.dailytask.presentation

data class Task(
    val name: String,
    val isCompleted: Boolean,
    val hasSubTasks: Boolean,
    val priority: TaskPriority
)