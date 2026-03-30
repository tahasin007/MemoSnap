package com.android.memosnap.feature.note.presentation.addeditnote

import androidx.compose.ui.graphics.toArgb
import com.android.memosnap.core.theme.DodgerBlue
import com.android.memosnap.feature.note.domain.model.ChecklistItem
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteType

data class AddEditNoteState(
    val title: String = "",
    val content: String = "",
    val dateCreated: String = "",
    val color: Int = DodgerBlue.toArgb(),
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val imageData: ByteArray? = null,
    val categoryId: Int? = null,
    val priority: NotePriority = NotePriority.NONE,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val dueAt: Long? = null,
    val reminderAt: Long? = null,
    val noteType: NoteType = NoteType.REGULAR,
    val checklistItems: List<ChecklistItem> = emptyList(),
    val id: Int? = null
)