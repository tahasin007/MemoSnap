package com.android.memosnap.feature.note.presentation.addeditnote.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.android.memosnap.feature.note.domain.model.NoteTag

@Composable
fun TagListPopup(
    onDismiss: () -> Unit,
    addNewTag: () -> Unit,
    tagList: List<NoteTag>,
    initiallySelectedTags: List<NoteTag>, // Pass the previously selected tags
    onClickAddTag: (List<NoteTag>) -> Unit // Return the entire list of selected tags
) {
    // Pre-populate the selectedTags with the initially selected tags
    val selectedTags =
        remember { mutableStateListOf<NoteTag>().apply { addAll(initiallySelectedTags) } }

    Popup(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(onClick = onDismiss), // Handle outside click to dismiss
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // Prevent dismissing when clicking on the Card
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Header row with "Tag" text and Add button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tag",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        IconButton(onClick = {
                            onDismiss()
                            addNewTag()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }
                    }

                    // Scrollable list of tags with fixed height
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(tagList.size) { index ->
                                val tag = tagList[index]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedTags.contains(tag), // Check if tag is selected
                                        onCheckedChange = { isChecked ->
                                            if (isChecked) {
                                                selectedTags.add(tag)
                                            } else {
                                                selectedTags.remove(tag)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = tag.name, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    // Cancel and OK buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "Cancel")
                        }

                        TextButton(onClick = {
                            onDismiss()
                            onClickAddTag(selectedTags.toList()) // Pass selected tags to the callback
                        }) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }
}

