package com.android.memosnap.feature.dailytask.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.feature.dailytask.domain.model.SubTask
import com.android.memosnap.feature.dailytask.presentation.NewTaskState
import com.android.memosnap.feature.dailytask.presentation.TaskPriority
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CodeBranch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    newTask: NewTaskState,
    categories: List<String>,
    onDismissBottomSheet: () -> Unit,
    onTaskNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onAddSubTask: () -> Unit,
    onSubTaskChange: (Int, SubTask) -> Unit,
    onRemoveSubTask: (Int) -> Unit,
    onAddTask: () -> Unit,
    isBottomSheetOpen: Boolean = false,
    textSize: TextUnit = 16.sp,
    singleLine: Boolean = false,
    maxLines: Int = 5
) {
    val bottomSheetState = rememberModalBottomSheetState()

    if (isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissBottomSheet,
            sheetState = bottomSheetState,
            dragHandle = {
                Spacer(modifier = Modifier)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Main Task Input
                BasicTextField(
                    value = newTask.taskName,
                    onValueChange = onTaskNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp),
                    singleLine = singleLine,
                    maxLines = maxLines,
                    textStyle = TextStyle.Default.copy(
                        fontSize = textSize,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            if (newTask.taskName.isEmpty()) {
                                Text(
                                    text = "Input new task here",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    fontSize = textSize
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Sub-tasks
                SubTaskListView(
                    subTasks = newTask.subTasks,
                    onSubTaskChange = onSubTaskChange,
                    onRemoveSubTask = onRemoveSubTask
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TaskCategoryDropdown(
                        selectedCategory = newTask.selectedCategory,
                        onCategoryChange = onCategoryChange,
                        categories = categories
                    )

                    IconButton(onClick = onAddSubTask) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(90f),
                            imageVector = FontAwesomeIcons.Solid.CodeBranch,
                            contentDescription = "Branch",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    TaskPriorityDropdown(
                        selectedPriority = newTask.priority,
                        onPriorityChange = onPriorityChange
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onAddTask) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Done",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(-90f)
                        )
                    }
                }
            }
        }
    }
}