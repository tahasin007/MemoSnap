package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddEditNoteAppBar(
    backgroundColor: Color,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onPaletteClick: () -> Unit,
    onPinClick: () -> Unit,
    isPinned: Boolean,
    isSaveEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Back Button
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.surface
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Save Button
            IconButton(
                onClick = onSaveClick,
                enabled = isSaveEnabled,
                modifier = Modifier.alpha(if (isSaveEnabled) 1f else 0.5f)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.surface
                )
            }

            // Palette Button
            IconButton(onClick = onPaletteClick) {
                Icon(
                    imageVector = Icons.Outlined.Palette,
                    contentDescription = "Palette",
                    tint = MaterialTheme.colorScheme.surface
                )
            }

            // Pin Button
            IconButton(onClick = onPinClick) {
                Icon(
                    imageVector = if (isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                    contentDescription = "Pin",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.rotate(45f)
                )
            }
        }
    }
}
