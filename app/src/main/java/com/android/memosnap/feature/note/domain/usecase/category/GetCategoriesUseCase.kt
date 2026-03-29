package com.android.memosnap.feature.note.domain.usecase.category

import com.android.memosnap.feature.note.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke() = repository.getAllCategories()
}

