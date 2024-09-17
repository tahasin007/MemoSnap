package com.android.memosnap.feature.dailytask.presentation.tasksscreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.android.memosnap.core.component.CommonDropdownMenuItem

@Composable
fun DailyTaskDropdownMenu(
    onDismiss: () -> Unit,
    onManageCategoryClick: () -> Unit,
    menuWidth: Dp = 200.dp,
    expanded: Boolean = false,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .width(menuWidth)
            .padding(0.dp),
        properties = PopupProperties(focusable = true)
    ) {
        CommonDropdownMenuItem(
            text = "Manage Category",
            onClick = onManageCategoryClick
        )

        CommonDropdownMenuItem(
            text = "Completed Tasks",
            onClick = {

            }
        )
    }
}