package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
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
    onArchiveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissed: () -> Unit,
    addNewTag: () -> Unit,
    isArchived: Boolean,
    tagList: List<NoteTag>,
    initiallySelectedTags: List<NoteTag>,
    onClickAddTag: (List<NoteTag>) -> Unit,
    isTagListVisible: Boolean = false,
    menuWidth: Dp = 150.dp,
    expanded: Boolean = false
) {
    var showTagPopup by remember { mutableStateOf(isTagListVisible) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissed,
        modifier = Modifier
            .width(menuWidth)
            .padding(0.dp),
        properties = PopupProperties(focusable = true)
    ) {
        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Tag,
            text = "Add Tag",
            onClick = {
                onDismissed()
                showTagPopup = true
            }
        )

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

    if (showTagPopup) {
        TagListPopup(
            onDismiss = { showTagPopup = false },
            addNewTag = addNewTag,
            tagList = tagList,
            initiallySelectedTags = initiallySelectedTags,
            onClickAddTag = onClickAddTag
        )
    }
}