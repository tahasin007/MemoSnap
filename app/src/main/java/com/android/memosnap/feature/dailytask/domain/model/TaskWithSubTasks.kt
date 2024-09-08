package com.android.memosnap.feature.dailytask.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithSubTasks(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val subTasks: List<SubTask>
)