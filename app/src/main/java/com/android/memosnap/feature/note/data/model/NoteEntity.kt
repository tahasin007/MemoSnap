package com.android.memosnap.feature.note.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteType

@Entity(tableName = "note")
data class NoteEntity(
    val title: String,
    val content: String,
    val dateCreated: String,
    val color: Int,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val imageData: ByteArray? = null,
    @ColumnInfo(name = "categoryId") val categoryId: Int? = null,
    @ColumnInfo(
        name = "priority",
        defaultValue = "NONE"
    ) val priority: NotePriority = NotePriority.NONE,
    @ColumnInfo(name = "isCompleted", defaultValue = "0") val isCompleted: Boolean = false,
    @ColumnInfo(name = "completedAt") val completedAt: Long? = null,
    @ColumnInfo(name = "dueAt") val dueAt: Long? = null,
    @ColumnInfo(name = "reminderAt") val reminderAt: Long? = null,
    @ColumnInfo(
        name = "noteType",
        defaultValue = "REGULAR"
    ) val noteType: NoteType = NoteType.REGULAR,
    @PrimaryKey val id: Int? = null
)

