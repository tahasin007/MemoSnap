package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorBox(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val innerPadding by animateDpAsState(
        targetValue = if (isSelected) 0.dp else 2.5.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(innerPadding)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .background(Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color)
        )
    }
}

