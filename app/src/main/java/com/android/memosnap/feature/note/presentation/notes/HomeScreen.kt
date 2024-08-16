package com.android.memosnap.feature.note.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.feature.note.presentation.notes.components.EditFloatingActionButton
import com.android.memosnap.feature.note.presentation.notes.components.HomeScreenAppBar
import com.android.memosnap.feature.note.presentation.notes.components.NoteCard
import com.android.memosnap.ui.screens.Screen

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteState = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            EditFloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditNote.route)
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            HomeScreenAppBar(
                onArchiveClick = {},
                onTagsClick = { navController.navigate(Screen.NoteTags.route) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
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

