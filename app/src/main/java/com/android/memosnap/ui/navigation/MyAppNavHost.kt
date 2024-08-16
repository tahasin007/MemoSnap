package com.android.memosnap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.memosnap.feature.note.presentation.addeditnote.AddEditNoteScreen
import com.android.memosnap.feature.note.presentation.archivednotes.ArchivedNotesScreen
import com.android.memosnap.feature.note.presentation.notes.HomeScreen
import com.android.memosnap.feature.note.presentation.notesbytag.NotesByTagScreen
import com.android.memosnap.feature.note.presentation.notetags.NoteTagScreen
import com.android.memosnap.ui.screens.DailyTaskScreen
import com.android.memosnap.ui.screens.Screen
import com.android.memosnap.ui.screens.SearchScreen

@Composable
fun MyAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.ArchivedNotes.route) { ArchivedNotesScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.DailyTask.route) { DailyTaskScreen() }
        composable(Screen.NoteTags.route) { NoteTagScreen(navController) }

        composable(
            route = Screen.NotesByTags.route + "?tagId={tagId}",
            arguments = listOf(navArgument("tagId") {
                type = NavType.IntType; defaultValue = -1
            })
        ) {
            NotesByTagScreen(navController = navController)
        }

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