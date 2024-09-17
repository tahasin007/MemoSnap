package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int?) {
        repository.deleteCategory(categoryId)
    }
}