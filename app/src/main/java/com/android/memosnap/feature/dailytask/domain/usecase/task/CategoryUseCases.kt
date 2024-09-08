package com.android.memosnap.feature.dailytask.domain.usecase.task

data class CategoryUseCases(
    val getAllCategories: GetCategoriesUseCase,
    val insertCategory: InsertCategoryUseCase,
    val deleteCategory: DeleteCategoryUseCase,
    val getCategoryByName: GetCategoryByNameUseCase,
    val getCategoryById: GetCategoryByIdUseCase
)