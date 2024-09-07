package com.android.memosnap.feature.dailytask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.feature.dailytask.presentation.TaskPriority
import com.android.memosnap.ui.theme.BlueColor
import com.android.memosnap.ui.theme.GrayColor
import com.android.memosnap.ui.theme.GreenColor
import com.android.memosnap.ui.theme.RedColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Flag
import compose.icons.fontawesomeicons.solid.CodeBranch

@Composable
fun TaskListItem(
    taskName: String,
    isTaskCompleted: Boolean,
    hasSubTasks: Boolean,
    onTaskCheckedChange: (Boolean) -> Unit,
    taskPriority: TaskPriority,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = .1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensure spacing between task name and flag
        ) {
            Row(
                modifier = Modifier.weight(1f), // Apply weight to this Row to allow spacing
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomRoundedCheckbox(
                    checked = isTaskCompleted,
                    onCheckedChange = onTaskCheckedChange
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = taskName,
                        fontSize = 16.sp,
                    )

                    // Subtask icon
                    if (hasSubTasks) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(90f),
                                imageVector = FontAwesomeIcons.Solid.CodeBranch,
                                contentDescription = "Has Subtask",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // The flag icon should be at the end, using the existing space
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 5.dp),
                imageVector = FontAwesomeIcons.Regular.Flag,
                contentDescription = "Priority",
                tint = when (taskPriority) {
                    TaskPriority.HIGH -> RedColor
                    TaskPriority.MEDIUM -> BlueColor
                    TaskPriority.LOW -> GreenColor
                    TaskPriority.NONE -> GrayColor
                }
            )
        }
    }
}