package com.android.memosnap.feature.note.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NoteTag
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagDao {
    @Query("SELECT * FROM notetag")
    fun getNoteTags(): Flow<List<NoteTag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTag(noteTag: NoteTag)

    @Delete
    suspend fun deleteNoteTag(noteTag: NoteTag)

    // Get notes by tagId
    @Transaction
    @Query(
        """
        SELECT * FROM note 
        INNER JOIN NoteTagCrossRef 
        ON note.id = NoteTagCrossRef.noteId
        WHERE NoteTagCrossRef.tagId = :tagId
    """
    )
    fun getNotesByTag(tagId: Int): Flow<List<Note>>

    @Query("SELECT * FROM notetag WHERE id = :tagId")
    suspend fun getTagById(tagId: Int): NoteTag
}