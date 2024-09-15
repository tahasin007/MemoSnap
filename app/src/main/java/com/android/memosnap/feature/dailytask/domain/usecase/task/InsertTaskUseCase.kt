package com.android.memosnap.feature.dailytask.domain.usecase.task

import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository

class InsertTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Long {
        return repository.insertTask(task)
    }
}