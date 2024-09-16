package com.android.memosnap.feature.dailytask.presentation.tasksscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.core.theme.BlueColor
import com.android.memosnap.core.theme.GrayColor
import com.android.memosnap.core.theme.GreenColor
import com.android.memosnap.core.theme.RedColor
import com.android.memosnap.feature.dailytask.presentation.shared.component.CustomRoundedCheckbox
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TaskPriority
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
    taskPriority: TaskPriority,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit,
    onTaskCheckedChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                onClick = onItemClicked,
                indication = rememberRipple(
                    bounded = true,
                    radius = 300.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = .1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
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