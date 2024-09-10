package com.android.memosnap.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.DailyTaskScreen
import com.android.memosnap.feature.note.presentation.addeditnote.AddEditNoteScreen
import com.android.memosnap.feature.note.presentation.archivednotes.ArchivedNotesScreen
import com.android.memosnap.feature.note.presentation.notes.HomeScreen
import com.android.memosnap.feature.note.presentation.notesbytag.NotesByTagScreen
import com.android.memosnap.feature.note.presentation.tags.NoteTagScreen
import com.android.memosnap.ui.screens.Screen
import com.android.memosnap.ui.screens.SearchScreen

@Composable
fun MyAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.ArchivedNotes.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
        ) { ArchivedNotesScreen(navController) }
        composable(
            route = Screen.Home.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
        ) { HomeScreen(navController) }
        composable(
            route = Screen.Search.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
        ) { SearchScreen() }
        composable(
            route = Screen.DailyTask.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
        ) { DailyTaskScreen() }

        composable(
            route = Screen.NoteTags.route + "?showAddTagPopup={showAddTagPopup}",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            arguments = listOf(navArgument("showAddTagPopup") {
                type = NavType.BoolType; defaultValue = false
            })
        ) { NoteTagScreen(navController) }

        composable(
            route = Screen.NotesByTags.route + "?tagId={tagId}",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            arguments = listOf(navArgument("tagId") {
                type = NavType.IntType; defaultValue = -1
            })
        ) {
            NotesByTagScreen(navController = navController)
        }

        composable(
            route = Screen.AddEditNote.route + "?noteId={noteId}&showTagListPopup={showTagListPopup}",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType; defaultValue = -1
                },
                navArgument("showTagListPopup") {
                    type = NavType.BoolType; defaultValue = false
                }
            )
        ) {
            AddEditNoteScreen(navController = navController)
        }
    }
}

// Function to handle enter transition
private fun enterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            750, easing = LinearOutSlowInEasing // Smoother ease-in effect
        )
    ) + slideInHorizontally(
        animationSpec = tween(
            750,
            easing = CubicBezierEasing(0.5f, 1.4f, 0.5f, 1f)
        ), // Adding a bounce effect
        initialOffsetX = { fullWidth -> fullWidth }  // Slide in from right
    ) + scaleIn(
        initialScale = 0.95f, // Slight scaling for a subtle zoom effect
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )
}


// Function to handle exit transition
private fun exitTransition(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            600, easing = LinearEasing // Smooth fade out
        )
    ) + slideOutHorizontally(
        animationSpec = tween(
            600,
            easing = CubicBezierEasing(0.8f, 0.0f, 0.2f, 1f) // Smooth and controlled slide out
        ),
        targetOffsetX = { fullWidth -> -fullWidth }  // Slide out to the left
    ) + scaleOut(
        targetScale = 0.9f, // Slight shrink during the exit for a more dynamic feel
        animationSpec = tween(
            500, easing = FastOutSlowInEasing // Shrink slightly faster than the slide
        )
    ) + fadeOut(
        animationSpec = tween(
            300,
            easing = FastOutLinearInEasing // Fading out faster towards the end for a snappy exit
        )
    )
}