package com.android.memosnap.feature.dailytask.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.memosnap.feature.dailytask.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Query("DELETE FROM category WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)

    @Query("SELECT * FROM category WHERE name = :categoryName LIMIT 1")
    suspend fun getCategoryByName(categoryName: String): Category?

    @Query("SELECT * FROM category WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): Category?
}