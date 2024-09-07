package com.android.memosnap.feature.dailytask.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.memosnap.data.sample.dummyTasks
import com.android.memosnap.feature.dailytask.presentation.components.AddTaskBottomSheet
import com.android.memosnap.feature.dailytask.presentation.components.CategoryButton
import com.android.memosnap.feature.dailytask.presentation.components.TaskListContent
import com.android.memosnap.ui.component.AppFloatingActionButton

@Composable
fun DailyTaskScreen(viewModel: DailyTaskViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.value
    val newTaskState = viewModel.newTaskState.value

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
                items(newTaskState.categories.size) { index ->
                    CategoryButton(text = newTaskState.categories[index]) {

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
                TaskListContent(tasks = dummyTasks) { _, _ ->

                }
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
                    viewModel.onEvent(DailyTaskEvent.EditedSubTask(index, subTask))
                },
                onRemoveSubTask = {
                    viewModel.onEvent(DailyTaskEvent.RemoveSubTask(it))
                },
                newTask = newTaskState
            )
        }
    }
}