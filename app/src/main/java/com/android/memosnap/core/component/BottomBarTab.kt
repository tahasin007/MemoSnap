package com.android.memosnap.core.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.memosnap.core.screens.Screen

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

    data object Home : BottomBarTab(
        route = Screen.Home.route,
        label = Screen.Home.label,
        activeIcon = Icons.Filled.Home,
        inactiveIcon = Icons.Outlined.Home
    )

    data object Search : BottomBarTab(
        route = Screen.Search.route,
        label = Screen.Search.label,
        activeIcon = Icons.Filled.Search,
        inactiveIcon = Icons.Outlined.Search
    )
}
