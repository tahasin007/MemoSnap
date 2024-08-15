package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.ui.graphics.toArgb
import com.android.memosnap.ui.theme.DodgerBlue

data class AddEditNoteState(
    val title: String = "",
    val content: String = "",
    val dateCreated: String = "",
    val color: Int = DodgerBlue.toArgb(),
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val id: Int? = null
)