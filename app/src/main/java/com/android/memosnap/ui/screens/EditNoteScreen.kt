package com.android.memosnap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.ui.component.EditeNoteTextField
import com.android.memosnap.utils.NoteUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(onBack: () -> Unit) {
    val viewModel = EditTextViewModel()
    val noteState = viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "Done",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Outlined.Palette,
                            contentDescription = "Palette",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Outlined.PushPin,
                            contentDescription = "PushPin",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(padding)
        ) {
            EditeNoteTextField(
                text = noteState.value.title,
                hint = "Title",
                onValueChange = {
                    viewModel.onEvent(EditNoteEvent.EnteredTitle(it))
                },
                maxLines = 2,
                textSize = 24.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = noteState.value.data.ifEmpty { NoteUtils.getCurrentFormattedDate() },
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                fontStyle = FontStyle.Italic,
            )

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            EditeNoteTextField(
                text = noteState.value.body,
                hint = "Add Note",
                onValueChange = {
                    viewModel.onEvent(EditNoteEvent.EnteredBody(it))
                },
                modifier = Modifier.fillMaxHeight(),
                textSize = 16.sp
            )
        }
    }
}
