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

    override suspend fun deleteCategory(categoryId: Int?) {
        // Delete associated tasks first
        categoryId?.let {
            taskDao.deleteTasksByCategoryId(it)
        }
        // Then delete the category itself
        categoryId?.let {
            categoryDao.deleteCategoryById(it)
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