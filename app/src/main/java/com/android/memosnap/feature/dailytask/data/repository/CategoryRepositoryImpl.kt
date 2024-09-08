package com.android.memosnap.feature.dailytask.data.repository

import com.android.memosnap.feature.dailytask.data.source.CategoryDao
import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val taskDao: TaskDao
) : CategoryRepository {
    override suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        // Delete associated tasks first
        category.id?.let { categoryId ->
            taskDao.deleteTasksByCategoryId(categoryId)
        }
        // Then delete the category itself
        category.id?.let { categoryId ->
            categoryDao.deleteCategoryById(categoryId)
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun getCategoryByName(categoryName: String): Category? {
        return categoryDao.getCategoryByName(categoryName)
    }

    override suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDao.getCategoryById(categoryId)
    }
}