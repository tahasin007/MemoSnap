package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class GetTaskUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: Int) = repository.getTask(taskId)
}
