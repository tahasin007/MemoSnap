package com.android.memosnap.feature.dailytask.domain.usecase

import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getAllTasks()
}