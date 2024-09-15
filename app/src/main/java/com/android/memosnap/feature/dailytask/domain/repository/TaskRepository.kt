package com.android.memosnap.feature.dailytask.domain.repository

import com.android.memosnap.feature.dailytask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>

    suspend fun getTask(taskId: Int): Task?

    suspend fun insertTask(task: Task): Long

    suspend fun deleteTask(task: Task)
}