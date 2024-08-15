package com.android.memosnap.ui.screens

sealed class Screen(val route: String, val label: String) {
    data object AddEditNote : Screen("AddEditNote", "AddEditNote")
    data object ArchivedNotes : Screen("archived_notes", "ArchivedNotes")
    data object Home : Screen("home", "Home")
    data object Search : Screen("search", "Search")
    data object DailyTask : Screen("dailyTask", "DailyTask")
}
