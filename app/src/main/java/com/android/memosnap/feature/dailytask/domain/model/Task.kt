package com.android.memosnap.feature.dailytask.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TaskPriority

@Entity
data class Task(
    val name: String,
    val priority: TaskPriority,
    val isCompleted: Boolean = false,
    val taskNote: String? = null,
    @ColumnInfo(name = "categoryId") val categoryId: Int?,
    @ColumnInfo(name = "sub_tasks") val subTasks: List<SubTask>,
    @PrimaryKey val id: Int? = null
)