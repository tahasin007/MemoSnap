package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.feature.note.presentation.addeditnote.components.AddEditNoteAppBar
import com.android.memosnap.feature.note.presentation.addeditnote.components.BottomSheetContainer
import com.android.memosnap.feature.note.presentation.addeditnote.components.EditeNoteTextField
import com.android.memosnap.utils.NoteUtils

@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val noteState = viewModel.noteState
    val noteTagsState = viewModel.tagsState
    val uiState = viewModel.uiState

    val isSaveEnabled = viewModel.isNoteEdited()

    val backgroundColor by animateColorAsState(
        targetValue = Color(noteState.value.color),
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AddEditNoteAppBar(
            backgroundColor = backgroundColor,
            onBackClick = { navController.popBackStack() },
            onSaveClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
                navController.popBackStack()
            },
            onPaletteClick = {
                viewModel.onEvent(
                    AddEditNoteEvent.ChangeBottomSheetVisibility(
                        uiState.value.isBottomSheetOpen.not()
                    )
                )
            },
            onPinClick = {
                viewModel.onEvent(AddEditNoteEvent.ChangePinnedStatus(noteState.value.isPinned.not()))
            },
            onArchiveClick = {
                viewModel.onEvent(AddEditNoteEvent.ChangeArchiveStatus(noteState.value.isArchived.not()))
            },
            onDeleteClick = {
                viewModel.onEvent(AddEditNoteEvent.DeleteNote)
                navController.popBackStack()
            },
            isPinned = noteState.value.isPinned,
            isArchived = noteState.value.isArchived,
            isSaveEnabled = isSaveEnabled,
            tagList = noteTagsState.value.tags,
            onClickAddTag = {
                viewModel.onEvent(AddEditNoteEvent.AddTagToNote(noteState.value.id, it))
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            EditeNoteTextField(
                text = noteState.value.title,
                hint = "Title",
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                maxLines = 2,
                textSize = 24.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = noteState.value.dateCreated,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                )

                Text(
                    text = "  |  ${NoteUtils.getTotalCharacters(noteState.value.content)} characters",
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 1.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
            )

            EditeNoteTextField(
                text = noteState.value.content,
                hint = "Add Note",
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredBody(it))
                },
                modifier = Modifier.fillMaxHeight(),
                textSize = 16.sp
            )

            BottomSheetContainer(
                isBottomSheetOpen = uiState.value.isBottomSheetOpen,
                noteColor = backgroundColor,
                onDismiss = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeBottomSheetVisibility(it))
                },
                onColorChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeColor(it))
                }
            )
        }
    }
}
