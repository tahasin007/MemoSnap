package com.android.memosnap.feature.dailytask.presentation.edittask

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.dailytask.domain.usecase.category.CategoryUseCases
import com.android.memosnap.feature.dailytask.domain.usecase.task.TaskUseCases
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.CategoryState
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.NewTaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _editTaskState = mutableStateOf(NewTaskState())
    val editTaskState: State<NewTaskState> = _editTaskState

    private val _categoriesState = mutableStateOf(CategoryState())
    val categoriesState: State<CategoryState> = _categoriesState

    private var getCategoriesJob: Job? = null

    fun loadTask(taskId: Int?) {
        taskId?.let {
            getTask(taskId)
        }
        getCategories()
    }

    private fun getTask(taskId: Int) {
        viewModelScope.launch {
            val task = taskUseCases.getTask(taskId)
            val category = if (task?.categoryId != null) {
                categoryUseCases.getCategoryById(task.categoryId)
            } else {
                null
            }
            task?.let {
                _editTaskState.value = _editTaskState.value.copy(
                    taskName = it.name,
                    subTasks = it.subTasks.toMutableList(),
                    category = category?.name ?: "No Category",
                    isCompleted = it.isCompleted,
                    priority = it.priority,
                    taskId = taskId
                )
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getAllCategories().onEach { categories ->
            _categoriesState.value = _categoriesState.value.copy(categories = categories)
        }.launchIn(viewModelScope)
    }
}