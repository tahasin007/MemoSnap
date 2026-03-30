package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.memosnap.feature.note.domain.model.NoteTag

@Composable
fun TagListPopup(
    onDismiss: () -> Unit,
    onCreateTag: (String) -> Unit,
    onDeleteTag: (NoteTag) -> Unit,
    tagList: List<NoteTag>,
    initiallySelectedTags: List<NoteTag>,
    onClickAddTag: (List<NoteTag>) -> Unit
) {
    val popupShape = RoundedCornerShape(20.dp)
    val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)
    val elevatedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    val outlineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val secondaryTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
    val destructiveColor = MaterialTheme.colorScheme.error

    val selectedTags = remember {
        mutableStateListOf<NoteTag>().apply { addAll(initiallySelectedTags) }
    }
    var newTagName by remember { mutableStateOf("") }
    var pendingDeleteTag by remember { mutableStateOf<NoteTag?>(null) }

    val normalizedTagInput = newTagName.trim()
    val isDuplicateTagName = normalizedTagInput.isNotEmpty() &&
            tagList.any { it.name.equals(normalizedTagInput, ignoreCase = true) }
    val canCreateTag = normalizedTagInput.isNotEmpty() && !isDuplicateTagName

    fun isSelected(target: NoteTag): Boolean {
        return selectedTags.any { selected ->
            if (selected.id != null && target.id != null) {
                selected.id == target.id
            } else {
                selected.name.equals(target.name, ignoreCase = true)
            }
        }
    }

    fun removeSelection(target: NoteTag) {
        selectedTags.removeAll { selected ->
            if (selected.id != null && target.id != null) {
                selected.id == target.id
            } else {
                selected.name.equals(target.name, ignoreCase = true)
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {},
                colors = CardDefaults.cardColors(containerColor = containerColor),
                border = BorderStroke(1.dp, outlineColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = popupShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 18.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Manage tags",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Select tags for this note or create a new one.",
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryTextColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = newTagName,
                                onValueChange = { newTagName = it },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                placeholder = { Text("New tag") },
                                isError = isDuplicateTagName,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = outlineColor,
                                    cursorColor = MaterialTheme.colorScheme.primary,
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    errorBorderColor = destructiveColor,
                                    errorCursorColor = destructiveColor
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                modifier = Modifier
                                    .background(
                                        color = if (canCreateTag) {
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                        } else {
                                            elevatedColor
                                        },
                                        shape = CircleShape
                                    ),
                                enabled = canCreateTag,
                                onClick = {
                                    if (canCreateTag) {
                                        onCreateTag(normalizedTagInput)
                                        newTagName = ""
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Create tag",
                                    tint = if (canCreateTag) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        secondaryTextColor
                                    }
                                )
                            }
                        }

                        if (isDuplicateTagName) {
                            Text(
                                text = "Tag already exists",
                                color = destructiveColor,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp, max = 260.dp)
                    ) {
                        if (tagList.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = elevatedColor,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(horizontal = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.LocalOffer,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No tags yet",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Create your first tag above to organize notes.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = secondaryTextColor
                                )
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(tagList, key = { tag -> tag.id ?: tag.name }) { tag ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .background(
                                                color = elevatedColor,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = isSelected(tag),
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = MaterialTheme.colorScheme.primary,
                                                uncheckedColor = secondaryTextColor,
                                                checkmarkColor = MaterialTheme.colorScheme.onPrimary
                                            ),
                                            onCheckedChange = { checked ->
                                                if (checked) {
                                                    if (!isSelected(tag)) {
                                                        selectedTags.add(tag)
                                                    }
                                                } else {
                                                    removeSelection(tag)
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = tag.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.weight(1f)
                                        )
                                        IconButton(
                                            onClick = {
                                                pendingDeleteTag = tag
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.DeleteOutline,
                                                contentDescription = "Delete tag",
                                                tint = destructiveColor.copy(alpha = 0.9f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = secondaryTextColor
                            ),
                            onClick = onDismiss
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                onDismiss()
                                onClickAddTag(selectedTags.toList())
                            }
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }

    pendingDeleteTag?.let { tag ->
        AlertDialog(
            onDismissRequest = { pendingDeleteTag = null },
            containerColor = containerColor,
            iconContentColor = destructiveColor,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = secondaryTextColor,
            shape = popupShape,
            title = { Text("Delete tag") },
            text = { Text("Delete \"${tag.name}\" from all notes?") },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = secondaryTextColor
                    ),
                    onClick = { pendingDeleteTag = null }
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = destructiveColor
                    ),
                    onClick = {
                        onDeleteTag(tag)
                        removeSelection(tag)
                        pendingDeleteTag = null
                    }
                ) {
                    Text("Delete")
                }
            }
        )
    }
}
