package com.android.memosnap.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.memosnap.core.component.DrawerMenuContent
import com.android.memosnap.core.component.FluidBottomNavigationBar
import com.android.memosnap.core.navigation.MyAppNavHost
import com.android.memosnap.core.screens.Screen

@Composable
fun MemoSnapAppContent() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Get current route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenuContent { _ ->
                // Handle menu item clicks if needed
            }
        },
        content = {
            Scaffold(
                bottomBar = {
                    if (currentRoute == null ||
                        (currentRoute.contains(Screen.Home.route) ||
                                currentRoute.contains(Screen.DailyTask.route))
                    ) {
                        FluidBottomNavigationBar(
                            navController = navController,
                            drawerState = drawerState
                        )
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MyAppNavHost(navController = navController)
                }
            }
        }
    )
}