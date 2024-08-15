package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.feature.note.presentation.addeditnote.components.BottomSheetContainer
import com.android.memosnap.feature.note.presentation.addeditnote.components.EditeNoteTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val noteState = viewModel.noteState
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(noteState.value.color)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddEditNoteEvent.SaveNoteAdd)
                            navController.popBackStack()
                        },
                        enabled = viewModel.noteState.value.title.isNotBlank() &&
                                viewModel.noteState.value.content.isNotBlank(),
                        modifier = Modifier.alpha(
                            if (viewModel.noteState.value.title.isNotBlank() &&
                                viewModel.noteState.value.content.isNotBlank()
                            ) 1f else 0.5f
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(
                            AddEditNoteEvent.ChangeBottomSheetVisibility(
                                uiState.value.isBottomSheetOpen.not()
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Palette,
                            contentDescription = "Palette",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(
                            AddEditNoteEvent.ChangePinnedStatus(noteState.value.isPinned.not())
                        )
                    }) {
                        Icon(
                            imageVector = if (noteState.value.isPinned) Icons.Filled.PushPin
                            else Icons.Outlined.PushPin,
                            contentDescription = "PushPin",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(noteState.value.color))
                .padding(padding)
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

            Text(
                text = noteState.value.dateCreated,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                fontStyle = FontStyle.Italic,
            )

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 1.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(5.dp))

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
                noteColor = Color(noteState.value.color),
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
