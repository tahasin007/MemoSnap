package com.android.memosnap.feature.note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenAppBar(
    onArchiveClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "MemoSnap",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 26.sp,
            fontWeight = W900,
            style = MaterialTheme.typography.titleLarge
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Settings Button
//            IconButton(onClick = { }) {
//                Icon(
//                    imageVector = Icons.Outlined.Settings,
//                    contentDescription = "Pin",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "MoreVert",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                HomeScreenOptionsMenu(
                    onDismissed = { expanded = false },
                    onArchiveClick = onArchiveClick,
                    expanded = expanded
                )
            }
        }
    }
}