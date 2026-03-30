package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.memosnap.feature.note.domain.model.ChecklistItem

@Composable
fun ChecklistEditorSection(
    checklistItems: List<ChecklistItem>,
    onAddItem: () -> Unit,
    onEditItem: (index: Int, item: ChecklistItem) -> Unit,
    onMoveItemUp: (index: Int) -> Unit,
    onMoveItemDown: (index: Int) -> Unit,
    onRemoveItem: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Checklist (${checklistItems.count { it.isCompleted }}/${checklistItems.size})",
            color = MaterialTheme.colorScheme.surface
        )
        Button(onClick = onAddItem) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = "Add item")
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(checklistItems) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = item.isCompleted,
                    onCheckedChange = { checked ->
                        onEditItem(index, item.copy(isCompleted = checked))
                    }
                )

                BasicTextField(
                    value = item.text,
                    onValueChange = { text -> onEditItem(index, item.copy(text = text)) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.surface
                    )
                )

                IconButton(onClick = { onMoveItemUp(index) }, enabled = index > 0) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Move up",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                IconButton(
                    onClick = { onMoveItemDown(index) },
                    enabled = index < checklistItems.lastIndex
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Move down",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                IconButton(onClick = { onRemoveItem(index) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

