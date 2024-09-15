package com.android.memosnap.feature.dailytask.domain.usecase.task

import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}