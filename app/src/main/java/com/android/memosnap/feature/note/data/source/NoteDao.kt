package com.android.memosnap.feature.note.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.android.memosnap.feature.note.domain.model.Note
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTag
import com.android.memosnap.feature.note.domain.model.NoteTagCrossRef
import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.domain.model.NoteWithTagsAndCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // ── Basic CRUD ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Delete
    suspend fun deleteNote(note: Note)

    // ── Filtered queries ──────────────────────────────────────────────────────

    @Query("SELECT * FROM note WHERE categoryId = :categoryId AND isArchived = 0")
    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE noteType = :noteType AND isArchived = 0")
    fun getNotesByType(noteType: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isCompleted = 1 AND isArchived = 0")
    fun getCompletedNotes(): Flow<List<Note>>

    @Query(
        """
        SELECT * FROM note
        WHERE (title LIKE '%' || :query || '%'
            OR content LIKE '%' || :query || '%')
        AND isArchived = 0
        """
    )
    fun searchNotes(query: String): Flow<List<Note>>

    // ── Updates ───────────────────────────────────────────────────────────────

    @Query("UPDATE note SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :id")
    suspend fun updateNoteCompletion(id: Int, isCompleted: Boolean, completedAt: Long?)

    @Query("UPDATE note SET categoryId = :categoryId WHERE id = :id")
    suspend fun updateNoteCategory(id: Int, categoryId: Int?)

    @Query("UPDATE note SET priority = :priority WHERE id = :id")
    suspend fun updateNotePriority(id: Int, priority: NotePriority)

    @Query("UPDATE note SET dueAt = :dueAt WHERE id = :id")
    suspend fun updateNoteDueDate(id: Int, dueAt: Long?)

    @Query("UPDATE note SET noteType = :noteType WHERE id = :id")
    suspend fun updateNoteType(id: Int, noteType: NoteType)

    @Query("UPDATE note SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun nullifyCategoryOnNotes(categoryId: Int)

    // ── Full aggregate (note + tags + category) ───────────────────────────────

    @Transaction
    @Query("SELECT * FROM note WHERE isArchived = 0")
    fun getNotesWithTagsAndCategory(): Flow<List<NoteWithTagsAndCategory>>

    @Transaction
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteWithTagsAndCategory(id: Int): NoteWithTagsAndCategory?

    // ── Tag cross-ref helpers ─────────────────────────────────────────────────

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT * FROM notetag 
        INNER JOIN NoteTagCrossRef 
        ON notetag.id = NoteTagCrossRef.tagId
        WHERE NoteTagCrossRef.noteId = :noteId
        """
    )
    fun getTagsByNoteId(noteId: Int): Flow<List<NoteTag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(noteTagCrossRef: NoteTagCrossRef)

    @Query("DELETE FROM NoteTagCrossRef WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun removeTagFromNote(noteId: Int, tagId: Int)
}