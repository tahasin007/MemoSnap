package com.android.memosnap.feature.dailytask.domain.usecase.task

import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.deleteCategory(category)
    }
}