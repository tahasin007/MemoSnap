package com.android.memosnap.feature.dailytask.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.memosnap.feature.dailytask.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * @deprecated Task data has been migrated into the unified Note table (MIGRATION_3_4).
 * This DAO and the underlying `task` table will be dropped in MIGRATION_4_5.
 * Remove this file in Phase 4 when all task-domain UI and use cases are retired.
 */
@Deprecated("Task table will be dropped in MIGRATION_4_5. Use NoteDao instead.")
@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTask(taskId: Int): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Query("DELETE FROM task WHERE categoryId = :categoryId")
    suspend fun deleteTasksByCategoryId(categoryId: Int)

    @Delete
    suspend fun deleteTask(task: Task)
}