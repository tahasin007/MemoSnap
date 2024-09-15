package com.android.memosnap.feature.dailytask.data.repository

import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTask(taskId: Int): Task? {
        return taskDao.getTask(taskId)
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}