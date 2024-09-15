package com.android.memosnap.feature.dailytask.presentation.edittask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.memosnap.core.screens.Screen
import com.android.memosnap.feature.dailytask.presentation.edittask.component.AddNotesToTaskRow
import com.android.memosnap.feature.dailytask.presentation.edittask.component.EditTaskAppBar
import com.android.memosnap.feature.dailytask.presentation.shared.SubTaskListView
import com.android.memosnap.feature.dailytask.presentation.shared.TaskCategoryDropdown
import com.android.memosnap.feature.dailytask.presentation.shared.TaskPriorityDropdown

@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel,
    taskId: Int?
) {
    viewModel.loadTask(taskId)
    val categoriesState = viewModel.categoriesState.value
    val editTask = viewModel.editTaskState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        EditTaskAppBar(
            onClickBack = {
                navController.popBackStack()
            },
            label = Screen.EditTask.label
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Top row with Category and Priority dropdowns
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TaskCategoryDropdown(
                    categories = categoriesState.categories.map { it.name },
                    selectedCategory = editTask.category,
                    onCategoryChange = {},
                    onClickAddNewCategory = {}
                )

                TaskPriorityDropdown(
                    selectedPriority = editTask.priority,
                    onPriorityChange = {

                    }
                )
            }

            BasicTextField(
                value = editTask.taskName,
                onValueChange = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 4.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W400
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            SubTaskListView(
                subTasks = editTask.subTasks,
                onSubTaskChange = { _, _ ->

                },
                onRemoveSubTask = {},
                minHeight = 200.dp
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Add Sub-task",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AddNotesToTaskRow(
                onClickAddNotes = {
                    navController.navigate(Screen.AddNotesToTask.route + "?taskId=${editTask.taskId}")
                }
            )
        }
    }
}