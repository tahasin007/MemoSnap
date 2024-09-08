package com.android.memosnap.feature.dailytask.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.model.SubTask
import com.android.memosnap.feature.dailytask.domain.model.Task
import com.android.memosnap.feature.dailytask.domain.usecase.category.TaskUseCases
import com.android.memosnap.feature.dailytask.domain.usecase.task.CategoryUseCases
import com.android.memosnap.feature.dailytask.presentation.components.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _uiState = mutableStateOf(DailyTaskUiState())
    val uiState: State<DailyTaskUiState> = _uiState

    private val _newTaskState = mutableStateOf(NewTaskState())
    val newTaskState: State<NewTaskState> = _newTaskState

    private val _tasksState = mutableStateOf(TasksState())
    val tasksState: State<TasksState> = _tasksState

    private val _categoriesState = mutableStateOf(CategoryState())
    val categoriesState: State<CategoryState> = _categoriesState

    private var getTasksJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        getTasks()
        getCategories()
    }

    fun onEvent(event: DailyTaskEvent) {
        when (event) {
            is DailyTaskEvent.ChangeBottomSheetVisibility -> {
                if (event.isVisible != _uiState.value.isBottomSheetOpen) {
                    _uiState.value = _uiState.value.copy(isBottomSheetOpen = event.isVisible)
                }
            }

            is DailyTaskEvent.EnteredTaskName -> {
                if (event.name != _newTaskState.value.taskName) {
                    _newTaskState.value = _newTaskState.value.copy(taskName = event.name)
                }
            }

            is DailyTaskEvent.EditedSubTask -> {
                editedSubTask(event.index, event.subTask)
            }

            DailyTaskEvent.AddSubTask -> {
                val subTasks = _newTaskState.value.subTasks.toMutableList()
                subTasks.add(SubTask("", false))
                _newTaskState.value = _newTaskState.value.copy(subTasks = subTasks)
            }

            is DailyTaskEvent.RemoveSubTask -> {
                val subTasks = _newTaskState.value.subTasks.toMutableList()
                if (event.index >= 0 && event.index < subTasks.size) {
                    subTasks.removeAt(event.index)
                    _newTaskState.value = _newTaskState.value.copy(subTasks = subTasks)
                }
            }

            is DailyTaskEvent.SelectedCategory -> {
                if (event.category != _newTaskState.value.selectedCategory) {
                    _newTaskState.value =
                        _newTaskState.value.copy(selectedCategory = event.category)
                }
            }

            is DailyTaskEvent.SelectedPriority -> {
                if (event.priority != _newTaskState.value.priority) {
                    _newTaskState.value = _newTaskState.value.copy(priority = event.priority)
                }
            }

            DailyTaskEvent.SaveTask -> saveTask()
        }
    }

    private fun editedSubTask(index: Int, subTask: SubTask) {
        val subTasks = _newTaskState.value.subTasks.toMutableList()
        if (index >= 0 && index < subTasks.size) {
            val editedSubTask = subTasks[index]
            subTasks[index] =
                editedSubTask.copy(
                    subTaskName = subTask.subTaskName,
                    isCompleted = subTask.isCompleted
                )
            _newTaskState.value = _newTaskState.value.copy(subTasks = subTasks)
        }
    }

    private fun saveTask() {
        _uiState.value = _uiState.value.copy(isBottomSheetOpen = false)

        viewModelScope.launch {
            val selectedCategory =
                categoryUseCases.getCategoryByName(_newTaskState.value.selectedCategory)
            val categoryId = selectedCategory?.id
                ?: categoryUseCases.insertCategory(Category(name = "No Category")).toInt()

            val newTask = Task(
                name = _newTaskState.value.taskName,
                categoryId = categoryId,
                priority = _newTaskState.value.priority,
                isCompleted = _newTaskState.value.isCompleted,
                subTasks = _newTaskState.value.subTasks
            )
            taskUseCases.insertTask(newTask)
        }
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getAllTasks().onEach { tasks ->
            _tasksState.value = _tasksState.value.copy(
                tasks = tasks
            )
        }.launchIn(viewModelScope)
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getAllCategories().onEach { categories ->
            _categoriesState.value = _categoriesState.value.copy(
                categories = categories
            )
        }.launchIn(viewModelScope)
    }
}