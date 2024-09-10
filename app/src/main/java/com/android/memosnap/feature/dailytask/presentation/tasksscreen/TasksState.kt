package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import com.android.memosnap.feature.dailytask.domain.model.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
)