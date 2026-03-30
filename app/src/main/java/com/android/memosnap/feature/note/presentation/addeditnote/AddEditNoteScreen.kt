package com.android.memosnap.feature.note.presentation.addeditnote

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.scale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.presentation.addeditnote.components.AddEditNoteAppBar
import com.android.memosnap.feature.note.presentation.addeditnote.components.BottomSheetContainer
import com.android.memosnap.feature.note.presentation.addeditnote.components.ChecklistEditorSection
import com.android.memosnap.feature.note.presentation.addeditnote.components.EditeNoteTextField
import com.android.memosnap.feature.note.presentation.addeditnote.components.EditorShortcutPanel
import com.android.memosnap.feature.note.presentation.addeditnote.components.NoteMetadataRow
import com.android.memosnap.feature.note.presentation.addeditnote.components.SelectedImagePreview
import com.android.memosnap.feature.note.presentation.addeditnote.components.TagListView
import com.android.memosnap.feature.note.util.NoteUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val note = viewModel.noteState.value
    val categories = viewModel.categories.value
    val tags = viewModel.tagsState.value
    val tagsByNoteId = viewModel.tagsByNoteId.value
    val uiState = viewModel.uiState.value
    val isExistingNote = note.id != null

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

    LaunchedEffect(uiState.openImagePickerOnStart) {
        if (uiState.openImagePickerOnStart) {
            imagePickerLauncher.launch("image/*")
            viewModel.consumeOpenImagePickerRequest()
        }
    }

    LaunchedEffect(uiState.shouldCloseScreen) {
        if (uiState.shouldCloseScreen) {
            navController.popBackStack()
            viewModel.consumeCloseScreenRequest()
        }
    }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.consumeUserMessage()
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
            onDueDateClick = {
                showDatePicker(context = context) {
                    viewModel.onEvent(AddEditNoteEvent.SetDueDate(it))
                }
            },
            onAddChecklistClick = {
                if (note.noteType == NoteType.CHECKLIST) {
                    viewModel.onEvent(AddEditNoteEvent.ConvertNoteType(NoteType.REGULAR))
                } else {
                    viewModel.onEvent(AddEditNoteEvent.ConvertNoteType(NoteType.CHECKLIST))
                }
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
            onCreateTag = { name ->
                viewModel.onEvent(AddEditNoteEvent.CreateTag(name))
            },
            onDeleteTag = { tag ->
                viewModel.onEvent(AddEditNoteEvent.DeleteTag(tag))
            },
            onClickAddTag = {
                viewModel.onEvent(AddEditNoteEvent.AddTagToNote(it))
            },
            backgroundColor = backgroundColor,
            isPinned = note.isPinned,
            isArchived = note.isArchived,
            showAddChecklistAction = true,
            showArchiveDeleteActions = isExistingNote,
            priority = note.priority,
            isSaveEnabled = isSaveEnabled && !uiState.isSaving,
            categories = categories,
            selectedCategoryId = note.categoryId,
            onCategorySelected = {
                viewModel.onEvent(AddEditNoteEvent.SetCategory(it))
            },
            onPrioritySelected = {
                viewModel.onEvent(AddEditNoteEvent.SetPriority(it))
            },
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
                    .padding(bottom = 190.dp)
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

                NoteMetadataRow(
                    dateCreated = note.dateCreated,
                    contentLength = NoteUtils.getTotalCharacters(note.content)
                )

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

                if (note.noteType == NoteType.CHECKLIST) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ChecklistEditorSection(
                        checklistItems = note.checklistItems,
                        onAddItem = { viewModel.onEvent(AddEditNoteEvent.AddChecklistItem()) },
                        onEditItem = { index, item ->
                            viewModel.onEvent(AddEditNoteEvent.EditChecklistItem(index, item))
                        },
                        onMoveItemUp = { index ->
                            viewModel.onEvent(
                                AddEditNoteEvent.MoveChecklistItem(
                                    fromIndex = index,
                                    toIndex = index - 1
                                )
                            )
                        },
                        onMoveItemDown = { index ->
                            viewModel.onEvent(
                                AddEditNoteEvent.MoveChecklistItem(
                                    fromIndex = index,
                                    toIndex = index + 1
                                )
                            )
                        },
                        onRemoveItem = { index ->
                            viewModel.onEvent(AddEditNoteEvent.RemoveChecklistItem(index))
                        }
                    )
                }

                TagListView(
                    tags = tagsByNoteId.tags,
                    onRemoveTag = { tag ->
                        val updatedTags = tagsByNoteId.tags.filterNot { selected ->
                            selected.id?.let { selectedId -> selectedId == tag.id }
                                ?: (selected.name == tag.name)
                        }
                        viewModel.onEvent(AddEditNoteEvent.AddTagToNote(updatedTags))
                    }
                )
            }

            note.imageData?.let { bytes ->
                SelectedImagePreview(
                    imageData = bytes,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(end = 12.dp, bottom = 120.dp)
                )
            }

            EditorShortcutPanel(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
            )

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

private fun showDatePicker(context: android.content.Context, onDateSelected: (Long) -> Unit) {
    val now = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selected = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
            onDateSelected(selected.timeInMillis)
        },
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH)
    ).show()
}
