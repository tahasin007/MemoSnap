package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository

class GetCategoryByNameUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryName: String): Category? {
        return categoryRepository.getCategoryByName(categoryName)
    }
}