package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun MoreOptionsMenu(
    onArchiveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissed: () -> Unit,
    menuWidth: Dp = 150.dp,
    expanded: Boolean = false
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissed,
        modifier = Modifier
            .width(menuWidth)
            .padding(0.dp),
        properties = PopupProperties(focusable = true)
    ) {
        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Archive,
            text = "Archive",
            onClick = {
                onArchiveClick()
                onDismissed()
            }
        )

        AddEditDropdownMenuItem(
            icon = Icons.Outlined.Delete,
            text = "Delete",
            onClick = {
                onDeleteClick()
                onDismissed()
            }
        )
    }
}