package com.android.memosnap.feature.note.presentation.archivednotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.memosnap.core.component.EmptyNoteView
import com.android.memosnap.core.screens.Screen
import com.android.memosnap.feature.note.presentation.archivednotes.components.ArchivedNotesAppBar
import com.android.memosnap.feature.note.presentation.notes.components.NotesStaggeredGrid

@Composable
fun ArchivedNotesScreen(
    navController: NavController,
    viewModel: ArchivedNotesViewModel = hiltViewModel()
) {
    val noteState = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ArchivedNotesAppBar(
            onClickBack = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (noteState.notes.isEmpty()) {
                EmptyNoteView(
                    title = "No Notes Found",
                    description = "Your archive is currently empty!"
                )
            } else {
                NotesStaggeredGrid(
                    notes = noteState.notes,
                    onNoteClick = { note ->
                        navController.navigate(
                            Screen.AddEditNote.route + "?noteId=${note.id}"
                        )
                    }
                )
            }
        }
    }
}