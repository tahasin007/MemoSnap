package com.android.memosnap.feature.dailytask.presentation.managecategory

import com.android.memosnap.feature.dailytask.domain.model.Category

sealed class ManageCategoryEvent {
    data class AddEditCategory(val category: Category) : ManageCategoryEvent()
    data class UpdateEditCategory(val categoryId: Int?) : ManageCategoryEvent()
    data class UpdateAddCategoryPopup(val categoryName: String) : ManageCategoryEvent()
    data class DeleteCategory(val categoryId: Int?) : ManageCategoryEvent()
    data class ChangeAddCategoryPopupVisibility(val isVisible: Boolean) : ManageCategoryEvent()
}