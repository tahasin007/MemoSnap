package com.android.memosnap.feature.note.presentation.notes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.core.theme.BlueColor
import com.android.memosnap.core.theme.GrayColor
import com.android.memosnap.core.theme.GreenColor
import com.android.memosnap.core.theme.RedColor
import com.android.memosnap.feature.note.domain.model.ChecklistProgress
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier,
    categoryName: String? = null,
    tags: List<NoteTag> = emptyList(),
    checklistProgress: ChecklistProgress? = null,
    onToggleCompletion: ((Boolean) -> Unit)? = null
) {
    val contentColor = if (Color(note.color).luminance() > 0.55f) Color(0xFF1B1B1F) else Color.White

    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color(note.color))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = contentColor,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (categoryName != null || tags.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        tags.take(2).forEach { tag ->
                            Surface(
                                shape = CircleShape,
                                color = contentColor.copy(alpha = 0.16f)
                            ) {
                                Text(
                                    text = "#${tag.name}",
                                    maxLines = 1,
                                    color = contentColor,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        if (tags.size > 2) {
                            Surface(
                                shape = CircleShape,
                                color = contentColor.copy(alpha = 0.16f)
                            ) {
                                Text(
                                    text = "+${tags.size - 2}",
                                    color = contentColor,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }

                note.imageData?.let { bytes ->
                    val bitmap = remember(bytes) {
                        android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Note Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(108.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = contentColor.copy(alpha = 0.86f)
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (note.noteType == NoteType.CHECKLIST) {
                    val total = checklistProgress?.total ?: 0
                    val completed = checklistProgress?.completed ?: 0
                    val progress = if (total == 0) 0f else completed.toFloat() / total.toFloat()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completed/$total done",
                            color = contentColor,
                            style = MaterialTheme.typography.labelMedium
                        )

                        if (onToggleCompletion != null) {
                            Checkbox(
                                checked = note.isCompleted,
                                onCheckedChange = onToggleCompletion
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        color = contentColor,
                        trackColor = contentColor.copy(alpha = 0.24f),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (note.priority != NotePriority.NONE) {
                        Icon(
                            imageVector = Icons.Filled.Flag,
                            contentDescription = "Priority",
                            tint = when (note.priority) {
                                NotePriority.HIGH -> RedColor
                                NotePriority.MEDIUM -> BlueColor
                                NotePriority.LOW -> GreenColor
                                NotePriority.NONE -> GrayColor
                            },
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    note.dueAt?.let {
                        Text(
                            text = "Due ${formatEpoch(it)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.9f)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = note.dateCreated,
                        color = contentColor.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                }
            }
        }

        if (note.isPinned) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-6).dp)
                    .size(20.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.PushPin,
                    contentDescription = "Pinned",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(4.dp)
                        .rotate(35f)
                )
            }
        }
    }
}

private fun formatEpoch(epoch: Long): String {
    return try {
        SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(epoch))
    } catch (_: Exception) {
        ""
    }
}
