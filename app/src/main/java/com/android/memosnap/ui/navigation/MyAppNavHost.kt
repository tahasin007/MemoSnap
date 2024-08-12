package com.android.memosnap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.memosnap.ui.screens.DailyTaskScreen
import com.android.memosnap.ui.screens.FavouriteScreen
import com.android.memosnap.ui.screens.HomeScreen
import com.android.memosnap.ui.screens.Screen
import com.android.memosnap.ui.screens.SearchScreen

@Composable
fun MyAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Favourite.route) { FavouriteScreen() }
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.DailyTask.route) { DailyTaskScreen() }
    }
}
