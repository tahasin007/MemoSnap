package com.android.memosnap.feature.dailytask.presentation.tasksscreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryButton(
    text: String,
    onClick: () -> Unit,
    currentCategory: String
) {
    val isSelected = currentCategory == text

    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .shadow(1.5.dp, shape = MaterialTheme.shapes.small),
        shape = MaterialTheme.shapes.small,
        color = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}