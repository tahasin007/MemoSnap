package com.android.memosnap.feature.note.presentation.notesbytag

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
import com.android.memosnap.feature.note.presentation.notes.components.NoteCard
import com.android.memosnap.feature.note.presentation.notesbytag.component.NotesByTagAppBar

@Composable
fun NotesByTagScreen(
    navController: NavController,
    viewModel: NotesByTagViewModel = hiltViewModel()
) {
    val notes = viewModel.notesState.value
    val tag = viewModel.tagState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        NotesByTagAppBar(
            onClickBack = {
                navController.popBackStack()
            },
            tag = tag.tagName
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp
            ) {
                items(notes.notes.size) { index ->
                    NoteCard(
                        note = notes.notes[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                            }
                    )
                }
            }
        }
    }
}