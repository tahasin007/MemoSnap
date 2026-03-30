package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Folder
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag

@Composable
fun AddEditNoteAppBar(
    onBackClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onPaletteClick: () -> Unit,
    onImageClick: () -> Unit,
    onDueDateClick: () -> Unit,
    onAddChecklistClick: () -> Unit,
    onPinNoteClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onDeleteNoteClick: () -> Unit,
    onCreateTag: (String) -> Unit,
    onDeleteTag: (NoteTag) -> Unit,
    onClickAddTag: (List<NoteTag>) -> Unit,
    backgroundColor: Color,
    isPinned: Boolean,
    isArchived: Boolean,
    showAddChecklistAction: Boolean,
    showArchiveDeleteActions: Boolean,
    priority: NotePriority,
    isSaveEnabled: Boolean,
    categories: List<Category>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int?) -> Unit,
    onPrioritySelected: (NotePriority) -> Unit,
    tagList: List<NoteTag>,
    initiallySelectedTags: List<NoteTag>
) {
    var expanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var priorityExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(75.dp)
            .background(backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Back Button
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.surface
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Save Button
            IconButton(
                onClick = onSaveNoteClick,
                enabled = isSaveEnabled,
                modifier = Modifier.alpha(if (isSaveEnabled) 1f else 0.5f)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.surface
                )
            }

            Box {
                IconButton(onClick = { categoryExpanded = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Folder,
                        contentDescription = "Category",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                DropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("None") },
                        onClick = {
                            onCategorySelected(null)
                            categoryExpanded = false
                        }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (selectedCategoryId == category.id) {
                                        "${category.name} ✓"
                                    } else {
                                        category.name
                                    }
                                )
                            },
                            onClick = {
                                onCategorySelected(category.id)
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Box {
                IconButton(onClick = { priorityExpanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.Flag,
                        contentDescription = "Priority",
                        tint = priorityColor(priority)
                    )
                }

                DropdownMenu(
                    expanded = priorityExpanded,
                    onDismissRequest = { priorityExpanded = false }
                ) {
                    NotePriority.entries.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (item == priority) {
                                        "${item.label} ✓"
                                    } else {
                                        item.label
                                    }
                                )
                            },
                            onClick = {
                                onPrioritySelected(item)
                                priorityExpanded = false
                            }
                        )
                    }
                }
            }

            // Dropdown menu
            Box {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "MoreVert",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                AddEditNoteMoreOptionsMenu(
                    onPaletteClick = onPaletteClick,
                    onImageClick = onImageClick,
                    onDueDateClick = onDueDateClick,
                    onAddChecklistClick = onAddChecklistClick,
                    onPinClick = onPinNoteClick,
                    onArchiveClick = onArchiveClick,
                    onDeleteClick = onDeleteNoteClick,
                    onDismissed = { expanded = false },
                    onCreateTag = onCreateTag,
                    onDeleteTag = onDeleteTag,
                    isArchived = isArchived,
                    isPinned = isPinned,
                    showAddChecklistAction = showAddChecklistAction,
                    showArchiveDeleteActions = showArchiveDeleteActions,
                    tagList = tagList,
                    initiallySelectedTags = initiallySelectedTags,
                    onClickAddTag = onClickAddTag,
                    expanded = expanded,
                )
            }
        }
    }
}

@Composable
private fun priorityColor(priority: NotePriority): Color {
    return when (priority) {
        NotePriority.NONE -> MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        NotePriority.LOW -> Color(0xFF4CAF50)
        NotePriority.MEDIUM -> Color(0xFFFFA726)
        NotePriority.HIGH -> Color(0xFFE53935)
    }
}