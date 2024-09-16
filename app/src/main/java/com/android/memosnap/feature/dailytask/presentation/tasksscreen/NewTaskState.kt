package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import com.android.memosnap.feature.dailytask.domain.model.SubTask

data class NewTaskState(
    val taskName: String = "",
    val subTasks: MutableList<SubTask> = mutableListOf(),
    val category: String = "No Category",
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.NONE,
    val taskNote: String? = null,
    val taskId: Int? = null
)