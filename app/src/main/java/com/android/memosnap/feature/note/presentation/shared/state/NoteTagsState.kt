package com.android.memosnap.feature.note.presentation.shared.state

import com.android.memosnap.feature.note.domain.model.NoteTag

data class NoteTagsState(
    val tags: List<NoteTag> = emptyList()
)
