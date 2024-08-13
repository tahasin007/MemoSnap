package com.android.memosnap.ui.screens

sealed class Screen(val route: String, val label: String) {
    data object EditNote : Screen("editNote", "EditNote")
    data object Favourite : Screen("favourite", "Favourite")
    data object Home : Screen("home", "Home")
    data object Search : Screen("search", "Search")
    data object DailyTask : Screen("dailyTask", "DailyTask")
}
