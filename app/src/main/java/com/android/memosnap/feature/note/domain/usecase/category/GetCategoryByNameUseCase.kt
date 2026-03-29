package com.android.memosnap.feature.note.domain.usecase.category

import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.repository.CategoryRepository

class GetCategoryByNameUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryName: String): Category? {
        return repository.getCategoryByName(categoryName)
    }
}

