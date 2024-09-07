package com.android.memosnap.data.sample

import com.android.memosnap.feature.dailytask.presentation.Task
import com.android.memosnap.feature.dailytask.presentation.TaskPriority

val dummyTasks = listOf(
    Task(
        name = "Buy groceries",
        isCompleted = false,
        hasSubTasks = true,
        priority = TaskPriority.HIGH
    ),
    Task(
        name = "Prepare presentation",
        isCompleted = true,
        hasSubTasks = false,
        priority = TaskPriority.MEDIUM
    ),
    Task(
        name = "Morning workout",
        isCompleted = false,
        hasSubTasks = true,
        priority = TaskPriority.LOW
    ),
    Task(
        name = "Plan weekend trip",
        isCompleted = false,
        hasSubTasks = false,
        priority = TaskPriority.HIGH
    ),
    Task(
        name = "Clean the house",
        isCompleted = true,
        hasSubTasks = true,
        priority = TaskPriority.MEDIUM
    ),
    Task(
        name = "Reply to emails",
        isCompleted = false,
        hasSubTasks = false,
        priority = TaskPriority.LOW
    )
)