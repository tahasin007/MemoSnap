package com.android.memosnap.feature.dailytask.domain.model

data class SubTask(
    val subTaskName: String,
    val isCompleted: Boolean = false
)