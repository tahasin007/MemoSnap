package com.android.memosnap.feature.dailytask.presentation.managecategory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.memosnap.feature.dailytask.domain.usecase.category.CategoryUseCases
import com.android.memosnap.feature.dailytask.domain.usecase.task.TaskUseCases
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.CategoryState
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TasksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoryState())
    val categoriesState: State<CategoryState> = _categoriesState

    private val _tasksState = mutableStateOf(TasksState())
    val tasksState: State<TasksState> = _tasksState

    private val _editCategoryState = mutableStateOf(EditCategoryState())
    val editCategoryState: State<EditCategoryState> = _editCategoryState

    private val _uiState = mutableStateOf(ManageCategoryUiState())
    val uiState: State<ManageCategoryUiState> = _uiState

    private var getTasksJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        getTasks()
        getCategories()
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getAllTasks().onEach { tasks ->
            _tasksState.value = _tasksState.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getAllCategories().onEach { categories ->
            _categoriesState.value = _categoriesState.value.copy(categories = categories)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ManageCategoryEvent) {
        when (event) {
            is ManageCategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    categoryUseCases.deleteCategory(event.categoryId)
                }
            }

            is ManageCategoryEvent.AddEditCategory -> {
                viewModelScope.launch {
                    categoryUseCases.insertCategory(event.category)
                }
            }

            is ManageCategoryEvent.ChangeAddCategoryPopupVisibility -> {
                if (event.isVisible != _uiState.value.isAddCategoryPopupVisible) {
                    _uiState.value =
                        _uiState.value.copy(isAddCategoryPopupVisible = event.isVisible)
                }
            }

            is ManageCategoryEvent.UpdateEditCategory -> {
                if (event.categoryId != _editCategoryState.value.id) {
                    _editCategoryState.value = _editCategoryState.value.copy(id = event.categoryId)
                }
            }

            is ManageCategoryEvent.UpdateAddCategoryPopup -> {
                if (event.categoryName != _editCategoryState.value.name) {
                    _editCategoryState.value = _editCategoryState.value.copy(name = event.categoryName)
                }
            }
        }
    }
}