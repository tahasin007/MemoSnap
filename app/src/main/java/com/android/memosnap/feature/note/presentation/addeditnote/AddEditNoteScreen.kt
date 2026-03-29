package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.scale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.core.screens.Screen
import com.android.memosnap.feature.note.presentation.addeditnote.components.AddEditNoteAppBar
import com.android.memosnap.feature.note.presentation.addeditnote.components.BottomSheetContainer
import com.android.memosnap.feature.note.presentation.addeditnote.components.EditeNoteTextField
import com.android.memosnap.feature.note.presentation.addeditnote.components.TagListView
import com.android.memosnap.feature.note.util.NoteUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val note = viewModel.noteState.value
    val tags = viewModel.tagsState.value
    val tagsByNoteId = viewModel.tagsByNoteId.value
    val uiState = viewModel.uiState.value

    val isSaveEnabled = viewModel.isNoteEdited()

    val backgroundColor by animateColorAsState(
        targetValue = Color(note.color),
        animationSpec = tween(durationMillis = 500), label = ""
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            coroutineScope.launch(Dispatchers.IO) {
                val bytes = context.contentResolver.openInputStream(it)?.use { stream ->
                    val originalBitmap = android.graphics.BitmapFactory.decodeStream(stream)
                    originalBitmap?.let { bitmap ->
                        val maxDimension = 1024
                        val scaledBitmap =
                            if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
                                val scale =
                                    (maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height))
                                bitmap.scale(
                                    (bitmap.width * scale).toInt(),
                                    (bitmap.height * scale).toInt()
                                )
                            } else {
                                bitmap
                            }

                        java.io.ByteArrayOutputStream().use { outputStream ->
                            scaledBitmap.compress(
                                android.graphics.Bitmap.CompressFormat.JPEG,
                                80,
                                outputStream
                            )
                            outputStream.toByteArray()
                        }
                    }
                }
                viewModel.onEvent(AddEditNoteEvent.SelectImage(bytes))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AddEditNoteAppBar(
            onBackClick = { navController.popBackStack() },
            onSaveNoteClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
                navController.popBackStack()
            },
            onPaletteClick = {
                viewModel.onEvent(
                    AddEditNoteEvent.ChangeBottomSheetVisibility(
                        uiState.isBottomSheetOpen.not()
                    )
                )
            },
            onImageClick = {
                imagePickerLauncher.launch("image/*")
            },
            onPinNoteClick = {
                viewModel.onEvent(AddEditNoteEvent.ChangePinnedStatus(note.isPinned.not()))
            },
            onArchiveClick = {
                viewModel.onEvent(AddEditNoteEvent.ChangeArchiveStatus(note.isArchived.not()))
            },
            onDeleteNoteClick = {
                viewModel.onEvent(AddEditNoteEvent.DeleteNote)
                navController.popBackStack()
            },
            addNewTag = {
                navController.navigate(Screen.NoteTags.route + "?showAddTagPopup=true")
            },
            onClickAddTag = {
                viewModel.onEvent(AddEditNoteEvent.AddTagToNote(it))
            },
            backgroundColor = backgroundColor,
            isPinned = note.isPinned,
            isArchived = note.isArchived,
            isSaveEnabled = isSaveEnabled,
            isTagListVisible = uiState.isTagListVisible,
            tagList = tags.tags,
            initiallySelectedTags = tagsByNoteId.tags
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            // Wrap TextField and TagList in Column so they adjust relative to each other
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 140.dp)
            ) {
                EditeNoteTextField(
                    text = note.title,
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
                        text = note.dateCreated,
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                    )

                    Text(
                        text = "  |  ${NoteUtils.getTotalCharacters(note.content)} characters",
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
                    text = note.content,
                    hint = "Add Note",
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredBody(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textSize = 16.sp
                )

                TagListView(tags = tagsByNoteId.tags)
            }

            note.imageData?.let { bytes ->
                val bitmap = remember(bytes) {
                    android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                }
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Selected image",
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            BottomSheetContainer(
                isBottomSheetOpen = uiState.isBottomSheetOpen,
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
