package com.android.memosnap.feature.note.presentation.notes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun HomeScreenOptionsMenu(
    onDismissed: () -> Unit,
    menuWidth: Dp = 150.dp,
    expanded: Boolean = false,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissed,
        modifier = Modifier
            .width(menuWidth)
            .padding(0.dp),
        properties = PopupProperties(focusable = true)
    ) {
        HomeScreenDropdownMenuItem(
            icon = Icons.Outlined.Tag,
            text = "Tags",
            onClick = {
                onDismissed()
            }
        )

        HomeScreenDropdownMenuItem(
            icon = Icons.Outlined.Archive,
            text = "Archived",
            onClick = {
                onDismissed()
            }
        )
    }
}