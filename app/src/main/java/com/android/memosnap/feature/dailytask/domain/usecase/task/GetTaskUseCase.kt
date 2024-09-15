package com.android.memosnap.feature.dailytask.domain.usecase.task

import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class GetTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int) = repository.getTask(taskId)
}
