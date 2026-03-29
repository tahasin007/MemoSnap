package com.android.memosnap.feature.note.data

import androidx.room.TypeConverter
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteType

class NoteConverters {

    @TypeConverter
    fun fromNotePriority(priority: NotePriority): String = priority.name

    @TypeConverter
    fun toNotePriority(value: String): NotePriority =
        runCatching { NotePriority.valueOf(value) }.getOrDefault(NotePriority.NONE)

    @TypeConverter
    fun fromNoteType(type: NoteType): String = type.name

    @TypeConverter
    fun toNoteType(value: String): NoteType =
        runCatching { NoteType.valueOf(value) }.getOrDefault(NoteType.REGULAR)
}