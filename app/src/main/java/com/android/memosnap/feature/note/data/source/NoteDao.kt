package com.android.memosnap.feature.note.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.android.memosnap.feature.note.data.model.NoteEntity
import com.android.memosnap.feature.note.data.model.NoteTagCrossRef
import com.android.memosnap.feature.note.data.model.NoteTagEntity
import com.android.memosnap.feature.note.domain.model.ChecklistProgress
import com.android.memosnap.feature.note.domain.model.NotePriority
import com.android.memosnap.feature.note.domain.model.NoteTagLink
import com.android.memosnap.feature.note.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // ── Basic CRUD ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    // ── Filtered queries ──────────────────────────────────────────────────────

    @Query("SELECT * FROM note WHERE categoryId = :categoryId AND isArchived = 0")
    fun getNotesByCategory(categoryId: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE noteType = :noteType AND isArchived = 0")
    fun getNotesByType(noteType: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE isCompleted = 1 AND isArchived = 0")
    fun getCompletedNotes(): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM note
        WHERE (title LIKE '%' || :query || '%'
            OR content LIKE '%' || :query || '%')
        AND isArchived = 0
        """
    )
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM note n
        WHERE n.isArchived = 0
          AND (:categoryId IS NULL OR n.categoryId = :categoryId)
          AND (:noteType IS NULL OR n.noteType = :noteType)
          AND (:isCompleted IS NULL OR n.isCompleted = :isCompleted)
          AND (:pinnedOnly = 0 OR n.isPinned = 1)
          AND (
                :tagId IS NULL OR EXISTS (
                    SELECT 1
                    FROM NoteTagCrossRef c
                    WHERE c.noteId = n.id AND c.tagId = :tagId
                )
              )
        """
    )
    fun getFilteredNotes(
        categoryId: Int?,
        noteType: String?,
        isCompleted: Boolean?,
        tagId: Int?,
        pinnedOnly: Boolean
    ): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT c.noteId AS noteId, t.id AS tagId, t.name AS tagName
        FROM NoteTagCrossRef c
        INNER JOIN notetag t ON t.id = c.tagId
        WHERE c.noteId IN (:noteIds)
        """
    )
    fun getTagLinksForNotes(noteIds: List<Int>): Flow<List<NoteTagLink>>

    @Query(
        """
        SELECT noteId AS noteId,
               COUNT(*) AS total,
               SUM(CASE WHEN isCompleted = 1 THEN 1 ELSE 0 END) AS completed
        FROM checklist_item
        WHERE noteId IN (:noteIds)
        GROUP BY noteId
        """
    )
    fun getChecklistProgressForNotes(noteIds: List<Int>): Flow<List<ChecklistProgress>>

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
    fun getTagsByNoteId(noteId: Int): Flow<List<NoteTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(noteTagCrossRef: NoteTagCrossRef)

    @Query("DELETE FROM NoteTagCrossRef WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun removeTagFromNote(noteId: Int, tagId: Int)
}