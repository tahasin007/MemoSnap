package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteMetadataRow(
    dateCreated: String,
    contentLength: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = dateCreated,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
        )

        Text(
            text = "  |  $contentLength characters",
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            fontSize = 14.sp
        )
    }
}

