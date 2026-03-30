package com.android.memosnap.feature.note.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.android.memosnap.feature.note.data.model.NoteEntity
import com.android.memosnap.feature.note.data.model.NoteTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagDao {
    @Query("SELECT * FROM notetag")
    fun getNoteTags(): Flow<List<NoteTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTag(noteTag: NoteTagEntity)

    @Query("DELETE FROM NoteTagCrossRef WHERE tagId = :tagId")
    suspend fun deleteTagLinksByTagId(tagId: Int)

    @Query("DELETE FROM notetag WHERE id = :tagId")
    suspend fun deleteTagById(tagId: Int)

    @Transaction
    suspend fun deleteTagAndLinks(tagId: Int) {
        deleteTagLinksByTagId(tagId)
        deleteTagById(tagId)
    }

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT * FROM note 
        INNER JOIN NoteTagCrossRef 
        ON note.id = NoteTagCrossRef.noteId
        WHERE NoteTagCrossRef.tagId = :tagId
    """
    )
    fun getNotesByTag(tagId: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notetag WHERE id = :tagId")
    suspend fun getTagById(tagId: Int): NoteTagEntity
}