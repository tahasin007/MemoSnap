package com.android.memosnap.feature.dailytask.domain.usecase.category

data class TaskUseCases(
    val getAllTasks: GetAllTasksUseCase,
    val getTask: GetTaskUseCase,
    val insertTask: InsertTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
)