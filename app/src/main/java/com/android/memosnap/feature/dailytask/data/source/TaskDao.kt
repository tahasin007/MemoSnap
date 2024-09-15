package com.android.memosnap.feature.dailytask.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.memosnap.feature.dailytask.domain.model.Task
import kotlinx.coroutines.flow.Flow

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