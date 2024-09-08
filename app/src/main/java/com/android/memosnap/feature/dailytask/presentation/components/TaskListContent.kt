package com.android.memosnap.feature.dailytask.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.memosnap.feature.dailytask.domain.model.Task

@Composable
fun TaskListContent(
    tasks: List<Task>,
    onTaskCheckedChange: (Int, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            TaskListItem(
                taskName = task.name,
                isTaskCompleted = task.isCompleted,
                hasSubTasks = task.subTasks.isNotEmpty(),
                onTaskCheckedChange = { checked -> onTaskCheckedChange(index, checked) },
                taskPriority = task.priority
            )
        }
    }
}