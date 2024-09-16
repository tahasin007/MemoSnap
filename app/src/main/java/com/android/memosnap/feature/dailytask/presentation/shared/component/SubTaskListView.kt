package com.android.memosnap.feature.dailytask.presentation.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.feature.dailytask.domain.model.SubTask

@Composable
fun SubTaskListView(
    subTasks: List<SubTask>,
    onSubTaskChange: (Int, SubTask) -> Unit,
    onRemoveSubTask: (Int) -> Unit,
    minHeight: Dp = 0.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .verticalScroll(rememberScrollState())
    ) {
        subTasks.forEachIndexed { index, subTask ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Custom rounded checkbox
                CustomRoundedCheckbox(
                    checked = subTask.isCompleted,
                    onCheckedChange = {
                        onSubTaskChange(
                            index, subTask.copy(isCompleted = !subTask.isCompleted)
                        )
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = subTask.subTaskName,
                        onValueChange = {
                            onSubTaskChange(
                                index, subTask.copy(subTaskName = it)
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                            fontStyle = if (subTask.isCompleted) FontStyle.Italic else FontStyle.Normal,
                            textDecoration = if (subTask.isCompleted) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (subTask.subTaskName.isBlank()) {
                        Text(
                            text = "Input the sub-task",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                IconButton(
                    onClick = { onRemoveSubTask(index) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove sub-task",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}