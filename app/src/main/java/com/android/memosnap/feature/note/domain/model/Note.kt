package com.android.memosnap.feature.note.domain.model

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
        result = 31 * result + (id ?: 0)
        return result
    }
}