package com.android.memosnap.feature.dailytask.data.repository

import com.android.memosnap.feature.note.data.source.CategoryDao
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository
import com.android.memosnap.feature.note.domain.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val noteDao: NoteDao // replaces taskDao — nullifies notes on category delete
) : CategoryRepository {

    override suspend fun insertCategory(category: Category): Long =
        categoryDao.insertCategory(category)

    override suspend fun deleteCategory(categoryId: Int?) {
        categoryId?.let {
            // Nullify categoryId on affected notes — never delete user content
            noteDao.nullifyCategoryOnNotes(it)
            categoryDao.deleteCategoryById(it)
        }
    }

    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories()

    override suspend fun getCategoryByName(categoryName: String): Category? =
        categoryDao.getCategoryByName(categoryName)

    override suspend fun getCategoryById(categoryId: Int): Category? =
        categoryDao.getCategoryById(categoryId)
}