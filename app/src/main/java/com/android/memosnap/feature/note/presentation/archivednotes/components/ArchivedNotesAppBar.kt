package com.android.memosnap.feature.note.presentation.archivednotes.components

import androidx.compose.runtime.Composable
import com.android.memosnap.feature.note.presentation.components.BackTitleAppBar

@Composable
fun ArchivedNotesAppBar(onClickBack: () -> Unit) {
    BackTitleAppBar(
        title = "Archived Notes",
        onBackClick = onClickBack
    )
}