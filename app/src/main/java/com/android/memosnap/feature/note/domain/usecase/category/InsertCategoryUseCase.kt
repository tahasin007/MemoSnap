package com.android.memosnap.feature.note.domain.usecase.category

import com.android.memosnap.feature.note.domain.model.Category
import com.android.memosnap.feature.note.domain.repository.CategoryRepository

class InsertCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Long {
        return repository.insertCategory(category)
    }
}

