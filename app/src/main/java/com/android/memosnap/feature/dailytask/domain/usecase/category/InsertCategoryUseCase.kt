package com.android.memosnap.feature.dailytask.domain.usecase.category

import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository
import com.android.memosnap.feature.note.domain.model.Category

class InsertCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Long {
        return repository.insertCategory(category)
    }
}