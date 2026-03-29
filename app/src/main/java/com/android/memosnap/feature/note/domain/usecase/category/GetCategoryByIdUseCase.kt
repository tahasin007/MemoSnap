package com.android.memosnap.feature.note.domain.usecase.category

import com.android.memosnap.feature.note.domain.repository.CategoryRepository

class GetCategoryByIdUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) = repository.getCategoryById(categoryId)
}

