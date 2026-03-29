package com.android.memosnap.feature.note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Note) return false

        return title == other.title &&
                content == other.content &&
                dateCreated == other.dateCreated &&
                color == other.color &&
                isPinned == other.isPinned &&
                isArchived == other.isArchived &&
                id == other.id &&
                categoryId == other.categoryId &&
                priority == other.priority &&
                isCompleted == other.isCompleted &&
                completedAt == other.completedAt &&
                dueAt == other.dueAt &&
                reminderAt == other.reminderAt &&
                noteType == other.noteType &&
                (imageData?.contentEquals(other.imageData ?: byteArrayOf())
                    ?: (other.imageData == null))
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + dateCreated.hashCode()
        result = 31 * result + color
        result = 31 * result + isPinned.hashCode()
        result = 31 * result + isArchived.hashCode()
        result = 31 * result + (imageData?.contentHashCode() ?: 0)
        result = 31 * result + (categoryId ?: 0)
        result = 31 * result + priority.hashCode()
        result = 31 * result + isCompleted.hashCode()
        result = 31 * result + (completedAt?.hashCode() ?: 0)
        result = 31 * result + (dueAt?.hashCode() ?: 0)
        result = 31 * result + (reminderAt?.hashCode() ?: 0)
        result = 31 * result + noteType.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }
}