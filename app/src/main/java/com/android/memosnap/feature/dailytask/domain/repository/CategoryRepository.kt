package com.android.memosnap.feature.dailytask.domain.repository

import com.android.memosnap.feature.dailytask.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insertCategory(category: Category): Long

    suspend fun deleteCategory(categoryId: Int?)

    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategoryByName(categoryName: String): Category?

    suspend fun getCategoryById(categoryId: Int): Category?
}