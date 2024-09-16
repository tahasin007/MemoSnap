package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.components.AddTaskBottomSheet
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.components.CategoryButton
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.components.TaskListContent
import com.android.memosnap.core.component.AppFloatingActionButton
import com.android.memosnap.core.screens.Screen

@Composable
fun DailyTaskScreen(
    navController: NavController,
    viewModel: DailyTaskViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.value
    val newTaskState = viewModel.newTaskState.value
    val tasksState = viewModel.tasksState.value
    val categoriesState = viewModel.categoriesState.value

    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(selectedCategory) {
        viewModel.onEvent(DailyTaskEvent.LoadTasksByCategory(selectedCategory))
    }

    Scaffold(
        floatingActionButton = {
            AppFloatingActionButton(onClick = {
                viewModel.onEvent(DailyTaskEvent.ChangeBottomSheetVisibility(true))
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Categories Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // "All" button to show all tasks
                item {
                    CategoryButton(text = "All") {
                        selectedCategory = "All"
                    }
                }

                // Display categories
                items(categoriesState.categories.size) { index ->
                    val category = categoriesState.categories[index]
                    CategoryButton(text = category.name) {
                        selectedCategory = category.name
                    }
                }
            }

            // Content area
            Box(
                modifier = Modifier
                    .weight(1f) // This makes the content take the remaining space
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                TaskListContent(tasks = tasksState.tasks,
                    onTaskCheckedChange = { _, _ -> },
                    onTaskClick = {
                        val taskId = tasksState.tasks[it].id
                        navController.navigate(Screen.EditTask.route + "?taskId=$taskId")
                    })
            }

            // Add task bottom sheet
            AddTaskBottomSheet(
                onTaskNameChange = {
                    viewModel.onEvent(DailyTaskEvent.EnteredTaskName(it))
                },
                onDismissBottomSheet = {
                    viewModel.onEvent(DailyTaskEvent.ChangeBottomSheetVisibility(false))
                },
                isBottomSheetOpen = uiState.isBottomSheetOpen,
                onCategoryChange = {
                    viewModel.onEvent(DailyTaskEvent.SelectedCategory(it))
                },
                onPriorityChange = {
                    viewModel.onEvent(DailyTaskEvent.SelectedPriority(it))
                },
                onAddSubTask = {
                    viewModel.onEvent(DailyTaskEvent.AddSubTask)
                },
                onSubTaskChange = { index, subTask ->
                    viewModel.onEvent(DailyTaskEvent.EditSubTask(index, subTask))
                },
                onRemoveSubTask = {
                    viewModel.onEvent(DailyTaskEvent.RemoveSubTask(it))
                },
                onAddTask = {
                    viewModel.onEvent(DailyTaskEvent.SaveTask)
                },
                changeCategoryPopupVisibility = {
                    viewModel.onEvent(DailyTaskEvent.ChangeAddCategoryPopupVisibility(it))
                },
                addNewCategory = {
                    viewModel.onEvent(DailyTaskEvent.AddCategory(it))
                },
                newTask = newTaskState,
                categories = categoriesState.categories.map { it.name },
                isAddCategoryPopupVisible = uiState.isAddCategoryPopupVisible
            )
        }
    }
}