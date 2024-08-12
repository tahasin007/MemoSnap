package com.android.memosnap.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.memosnap.ui.screens.Screen
import kotlinx.coroutines.launch

@Composable
fun FluidBottomNavigationBar(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val items = listOf(
        Screen.Drawer,
        Screen.Favourite,
        Screen.Home,
        Screen.Search,
        Screen.DailyTask
    )

    val activeIcons = listOf(
        Icons.Filled.Menu,
        Icons.Filled.Favorite,
        Icons.Filled.Home,
        Icons.Filled.Search,
        Icons.Filled.TaskAlt
    )

    val inactiveIcons = listOf(
        Icons.Outlined.Menu,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.Home,
        Icons.Outlined.Search,
        Icons.Outlined.TaskAlt
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surface)
            .animateContentSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, screen ->
            val selected = currentRoute == screen.route
            val iconScale by animateFloatAsState(
                targetValue = if (selected) 1.2f else 1f,
                animationSpec = tween(durationMillis = 300), label = ""
            )
            val iconColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
                animationSpec = tween(durationMillis = 300), label = ""
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) activeIcons[index] else inactiveIcons[index],
                        contentDescription = screen.label,
                        tint = iconColor,
                        modifier = Modifier
                            .scale(iconScale)
                            .padding(vertical = 8.dp)
                    )
                },
                selected = selected,
                onClick = {
                    if (screen == Screen.Drawer) {
                        scope.launch {
                            drawerState.open()
                        }
                    } else if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                alwaysShowLabel = true,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}