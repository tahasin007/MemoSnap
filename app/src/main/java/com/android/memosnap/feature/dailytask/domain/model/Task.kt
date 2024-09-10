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
    @ColumnInfo(name = "sub_tasks") val subTasks: List<SubTask>,
    @ColumnInfo(name = "categoryId") val categoryId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)