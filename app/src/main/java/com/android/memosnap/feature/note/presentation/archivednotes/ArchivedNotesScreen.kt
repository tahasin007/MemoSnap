package com.android.memosnap.feature.note.presentation.archivednotes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.memosnap.feature.note.presentation.archivednotes.components.ArchivedNotesAppBar
import com.android.memosnap.feature.note.presentation.notes.components.NoteCard
import com.android.memosnap.core.component.EmptyNoteView
import com.android.memosnap.core.screens.Screen

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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp
                ) {
                    items(noteState.notes.size) { index ->
                        NoteCard(
                            note = noteState.notes[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) {
                                    navController.navigate(
                                        Screen.AddEditNote.route +
                                                "?noteId=${noteState.notes[index].id}"
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}