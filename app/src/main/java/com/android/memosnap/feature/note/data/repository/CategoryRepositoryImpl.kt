package com.android.memosnap.feature.note.data.repository

import com.android.memosnap.feature.note.data.model.toDomain
import com.android.memosnap.feature.note.data.model.toEntity
import com.android.memosnap.feature.note.data.source.CategoryDao
import com.android.memosnap.feature.note.data.source.NoteDao
import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val noteDao: NoteDao
) : CategoryRepository {

    override suspend fun insertCategory(category: Category): Long =
        categoryDao.insertCategory(category.toEntity())

    override suspend fun deleteCategory(categoryId: Int?) {
        categoryId?.let {
            // Nullify categoryId on affected notes so note content is never deleted.
            noteDao.nullifyCategoryOnNotes(it)
            categoryDao.deleteCategoryById(it)
        }
    }

    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { list -> list.map { it.toDomain() } }

    override suspend fun getCategoryByName(categoryName: String): Category? =
        categoryDao.getCategoryByName(categoryName)?.toDomain()

    override suspend fun getCategoryById(categoryId: Int): Category? =
        categoryDao.getCategoryById(categoryId)?.toDomain()
}

