package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.android.memosnap.feature.note.domain.model.NoteTag

@Composable
fun AddEditNoteMoreOptionsMenu(
    onPaletteClick: () -> Unit,
    onImageClick: () -> Unit,
    onDueDateClick: () -> Unit,
    onAddChecklistClick: () -> Unit,
    onPinClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissed: () -> Unit,
    onCreateTag: (String) -> Unit,
    onDeleteTag: (NoteTag) -> Unit,
    isArchived: Boolean,
    isPinned: Boolean,
    showAddChecklistAction: Boolean,
    showArchiveDeleteActions: Boolean,
    tagList: List<NoteTag>,
    initiallySelectedTags: List<NoteTag>,
    onClickAddTag: (List<NoteTag>) -> Unit,
    menuWidth: Dp = 190.dp,
    expanded: Boolean = false
) {
    var showTagPopup by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissed,
        modifier = Modifier
            .width(menuWidth)
            .padding(0.dp),
        properties = PopupProperties(focusable = true)
    ) {
        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Palette,
            text = "Change Color",
            onClick = {
                onDismissed()
                onPaletteClick()
            }
        )

        AddEditDropdownMenuItem(
            icon = Icons.Filled.Image,
            text = "Add Image",
            onClick = {
                onDismissed()
                onImageClick()
            }
        )

        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Event,
            text = "Due date",
            onClick = {
                onDismissed()
                onDueDateClick()
            }
        )

        if (showAddChecklistAction) {
            AddEditDropdownMenuItem(
                icon = Icons.Filled.Checklist,
                text = "Checklist",
                onClick = {
                    onDismissed()
                    onAddChecklistClick()
                }
            )
        }

        AddEditDropdownMenuItem(
            icon = if (isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
            text = if (isPinned) "Unpin" else "Pin",
            onClick = {
                onDismissed()
                onPinClick()
            }
        )

        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Tag,
            text = "Add Tag",
            onClick = {
                onDismissed()
                showTagPopup = true
            }
        )

        if (showArchiveDeleteActions) {
            AddEditDropdownMenuItem(
                icon = if (isArchived) Icons.Outlined.Unarchive else Icons.Outlined.Archive,
                text = if (isArchived) "Unarchive" else "Archive",
                onClick = {
                    onDismissed()
                    onArchiveClick()
                }
            )

            AddEditDropdownMenuItem(
                icon = Icons.Outlined.Delete,
                text = "Delete",
                onClick = {
                    onDismissed()
                    onDeleteClick()
                }
            )
        }
    }

    if (showTagPopup) {
        TagListPopup(
            onDismiss = { showTagPopup = false },
            onCreateTag = onCreateTag,
            onDeleteTag = onDeleteTag,
            tagList = tagList,
            initiallySelectedTags = initiallySelectedTags,
            onClickAddTag = onClickAddTag
        )
    }
}