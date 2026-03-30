package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.StrikethroughS
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditorShortcutPanel(
    modifier: Modifier = Modifier
) {
    val iconTint = MaterialTheme.colorScheme.primary
    val panelShape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape = panelShape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.FormatBold,
                        contentDescription = "Bold",
                        tint = iconTint
                    )
                }
            }
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.FormatItalic,
                        contentDescription = "Italic",
                        tint = iconTint
                    )
                }
            }
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.FormatUnderlined,
                        contentDescription = "Underline",
                        tint = iconTint
                    )
                }
            }
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.StrikethroughS,
                        contentDescription = "Strike through",
                        tint = iconTint
                    )
                }
            }
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                        contentDescription = "Bullet list",
                        tint = iconTint
                    )
                }
            }
            item {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Title,
                        contentDescription = "Header",
                        tint = iconTint
                    )
                }
            }
        }
    }
}