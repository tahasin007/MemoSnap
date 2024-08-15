package com.android.memosnap.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarTab(
    val route: String,
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    data object Drawer : BottomBarTab(
        route = "drawer",
        label = "Drawer",
        activeIcon = Icons.Filled.Menu,
        inactiveIcon = Icons.Outlined.Menu
    )

    data object Archived : BottomBarTab(
        route = "archived",
        label = "Archived",
        activeIcon = Icons.Filled.Archive,
        inactiveIcon = Icons.Outlined.Archive
    )

    data object Home : BottomBarTab(
        route = "home",
        label = "Home",
        activeIcon = Icons.Filled.Home,
        inactiveIcon = Icons.Outlined.Home
    )

    data object Search : BottomBarTab(
        route = "search",
        label = "Search",
        activeIcon = Icons.Filled.Search,
        inactiveIcon = Icons.Outlined.Search
    )

    data object DailyTask : BottomBarTab(
        route = "dailyTask",
        label = "DailyTask",
        activeIcon = Icons.Filled.TaskAlt,
        inactiveIcon = Icons.Outlined.TaskAlt
    )
}
