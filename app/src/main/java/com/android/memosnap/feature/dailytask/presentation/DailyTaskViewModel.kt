package com.android.memosnap.feature.dailytask.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyTaskViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(DailyTaskUiState())
    val uiState: State<DailyTaskUiState> = _uiState

    private val _newTaskState = mutableStateOf(NewTaskState())
    val newTaskState: State<NewTaskState> = _newTaskState

    init {
        _newTaskState.value = _newTaskState.value.copy(
            categories = listOf("No Category", "Work", "Personal", "Home", "Birthday")
        )
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
                val subTasks = _newTaskState.value.subTasks.toMutableList()
                if (event.index >= 0 && event.index < subTasks.size) {
                    val editedSubTask = subTasks[event.index]
                    subTasks[event.index] =
                        editedSubTask.copy(
                            subTaskName = event.subTask.subTaskName,
                            isCompleted = event.subTask.isCompleted
                        )
                    _newTaskState.value = _newTaskState.value.copy(subTasks = subTasks)
                }
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
        }
    }
}