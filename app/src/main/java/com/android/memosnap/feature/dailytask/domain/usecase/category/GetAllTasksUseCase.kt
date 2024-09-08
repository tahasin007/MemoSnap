package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getAllTasks()
}