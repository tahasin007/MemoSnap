package com.android.memosnap.ui.screens

import androidx.compose.ui.graphics.toArgb
import com.android.memosnap.ui.theme.DodgerBlue

data class EditTextState(
    val title: String = "",
    val body: String = "",
    val date: String = "",
    val color: Int = DodgerBlue.toArgb()
)