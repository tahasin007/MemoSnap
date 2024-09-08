package com.android.memosnap.feature.dailytask.domain.usecase

import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class GetTaskUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: Int) = repository.getTask(taskId)
}
