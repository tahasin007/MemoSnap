package com.android.memosnap.feature.dailytask.presentation.edittask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import com.android.memosnap.feature.dailytask.presentation.edittask.component.EditTaskAppBar

@Composable
fun AddNotesToTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel,
    taskId: Int?
) {
    viewModel.loadTask(taskId)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = editTask.notes,
                    onValueChange = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400
                    )
                )

                if (editTask.notes.isBlank()) {
                    Text(
                        text = "Add Notes",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}