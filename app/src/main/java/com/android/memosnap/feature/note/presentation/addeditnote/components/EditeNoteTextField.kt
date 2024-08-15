package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditeNoteTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 16.sp,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE
) {
    Box(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = textSize,
                color = MaterialTheme.colorScheme.surface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.surface)
        )
        if (text.isEmpty()) {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                fontSize = textSize
            )
        }
    }
}