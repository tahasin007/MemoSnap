package com.android.memosnap.feature.dailytask.domain.usecase

data class TaskUseCases(
    val getAllTasks: GetAllTasksUseCase,
    val getTask: GetTaskUseCase,
    val insertTask: InsertTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
)