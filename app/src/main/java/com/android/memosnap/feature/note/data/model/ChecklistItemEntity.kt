package com.android.memosnap.feature.note.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_item",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("noteId")]
)
data class ChecklistItemEntity(
    @ColumnInfo(name = "noteId") val noteId: Int,
    val text: String,
    val isCompleted: Boolean = false,
    val sortOrder: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

