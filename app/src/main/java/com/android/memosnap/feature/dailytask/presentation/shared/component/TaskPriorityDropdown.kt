package com.android.memosnap.feature.dailytask.presentation.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TaskPriority
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Flag

@Composable
fun TaskPriorityDropdown(
    selectedPriority: TaskPriority,
    onPriorityChange: (TaskPriority) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box {
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .width(150.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            TaskPriority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = priority.priority,
                            fontSize = 14.sp,
                            color = if (selectedPriority == priority) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onPriorityChange(priority)
                        isExpanded = false
                    }
                )
            }
        }
    }

    IconButton(onClick = {
        isExpanded = true
    }) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            imageVector = FontAwesomeIcons.Regular.Flag,
            contentDescription = "Flag",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}