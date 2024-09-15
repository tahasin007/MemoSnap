package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    val repository: CategoryRepository
) {
    operator fun invoke() = repository.getAllCategories()
}