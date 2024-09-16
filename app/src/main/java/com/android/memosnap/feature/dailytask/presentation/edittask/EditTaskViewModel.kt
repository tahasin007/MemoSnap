package com.android.memosnap.feature.dailytask.presentation.edittask

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.model.SubTask
import com.android.memosnap.feature.dailytask.domain.model.Task
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

    fun onEvent(event: EditTaskEvent) {
        when (event) {
            is EditTaskEvent.AddCategory -> {
                viewModelScope.launch {
                    categoryUseCases.insertCategory(Category(name = event.category))
                }
            }

            is EditTaskEvent.AddSubTask -> {
                val subTasks = _editTaskState.value.subTasks.toMutableList()
                subTasks.add(SubTask("", false))
                _editTaskState.value = _editTaskState.value.copy(subTasks = subTasks)
            }

            is EditTaskEvent.ChangeTaskName -> {
                if (event.name != _editTaskState.value.taskName) {
                    _editTaskState.value = _editTaskState.value.copy(taskName = event.name)
                }
            }

            is EditTaskEvent.EditSubTask -> {
                editedSubTask(event.index, event.subTask)
            }

            is EditTaskEvent.RemoveSubTask -> {
                val subTasks = _editTaskState.value.subTasks.toMutableList()
                if (event.index >= 0 && event.index < subTasks.size) {
                    subTasks.removeAt(event.index)
                    _editTaskState.value = _editTaskState.value.copy(subTasks = subTasks)
                }
            }

            is EditTaskEvent.ChangeCategory -> {
                if (event.category != _editTaskState.value.category) {
                    _editTaskState.value = _editTaskState.value.copy(category = event.category)
                }
            }

            is EditTaskEvent.ChangePriority -> {
                if (event.priority != _editTaskState.value.priority) {
                    _editTaskState.value = _editTaskState.value.copy(priority = event.priority)
                }
            }

            is EditTaskEvent.ChangeTaskNote -> {
                if (event.note != _editTaskState.value.taskNote) {
                    _editTaskState.value = _editTaskState.value.copy(taskNote = event.note)
                }
            }

            is EditTaskEvent.SaveTask -> saveTask()

            is EditTaskEvent.DeleteTask -> deleteTask()
        }
    }

    private fun editedSubTask(index: Int, subTask: SubTask) {
        val subTasks = _editTaskState.value.subTasks.toMutableList()
        if (index >= 0 && index < subTasks.size) {
            val editedSubTask = subTasks[index]
            subTasks[index] =
                editedSubTask.copy(
                    subTaskName = subTask.subTaskName,
                    isCompleted = subTask.isCompleted
                )
            _editTaskState.value = _editTaskState.value.copy(subTasks = subTasks)
        }
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

    private fun saveTask() {
        viewModelScope.launch {
            val selectedCategory =
                categoryUseCases.getCategoryByName(_editTaskState.value.category)
            val categoryId = selectedCategory?.id

            val newTask = Task(
                name = _editTaskState.value.taskName,
                categoryId = categoryId,
                priority = _editTaskState.value.priority,
                isCompleted = _editTaskState.value.isCompleted,
                subTasks = _editTaskState.value.subTasks,
                taskNote = _editTaskState.value.taskNote,
                id = _editTaskState.value.taskId
            )
            taskUseCases.insertTask(newTask)
        }
    }

    private fun deleteTask() {
        if (_editTaskState.value.taskId != null) {
            viewModelScope.launch {
                val selectedCategory =
                    categoryUseCases.getCategoryByName(_editTaskState.value.category)
                val categoryId = selectedCategory?.id

                taskUseCases.deleteTask(
                    Task(
                        name = _editTaskState.value.taskName,
                        categoryId = categoryId,
                        priority = _editTaskState.value.priority,
                        isCompleted = _editTaskState.value.isCompleted,
                        subTasks = _editTaskState.value.subTasks,
                        taskNote = _editTaskState.value.taskNote,
                        id = _editTaskState.value.taskId
                    )
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