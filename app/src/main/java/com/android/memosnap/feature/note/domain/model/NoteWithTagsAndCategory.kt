package com.android.memosnap.feature.note.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Full aggregate: a Note with its many Tags (via junction table) and its
 * optional single Category (via FK on note.categoryId).
 * Room requires @Relation fields to be `var` so its generated code can write them.
 * Use `category.firstOrNull()` at call sites.
 */
data class NoteWithTagsAndCategory(
    @Embedded val note: Note,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteTagCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "tagId"
        )
    )
    var tags: List<NoteTag> = emptyList(),

    // Room @Relation for a to-one FK: returned as List, take firstOrNull() at call sites
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var category: List<Category> = emptyList()
)
