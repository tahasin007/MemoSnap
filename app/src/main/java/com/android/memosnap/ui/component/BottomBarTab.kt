package com.android.memosnap.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.FavoriteBorder
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

    data object Favourite : BottomBarTab(
        route = "favourite",
        label = "Favourite",
        activeIcon = Icons.Filled.Favorite,
        inactiveIcon = Icons.Outlined.FavoriteBorder
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
