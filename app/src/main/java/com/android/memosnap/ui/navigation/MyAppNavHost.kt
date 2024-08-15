package com.android.memosnap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.memosnap.feature.note.presentation.addeditnote.AddEditNoteScreen
import com.android.memosnap.feature.note.presentation.notes.HomeScreen
import com.android.memosnap.ui.screens.ArchivedScreen
import com.android.memosnap.ui.screens.DailyTaskScreen
import com.android.memosnap.ui.screens.Screen
import com.android.memosnap.ui.screens.SearchScreen

@Composable
fun MyAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Archived.route) { ArchivedScreen() }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.DailyTask.route) { DailyTaskScreen() }

        composable(
            route = Screen.AddEditNote.route + "?noteId={noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.IntType; defaultValue = -1
            })
        ) {
            AddEditNoteScreen(navController = navController)
        }
    }
}
