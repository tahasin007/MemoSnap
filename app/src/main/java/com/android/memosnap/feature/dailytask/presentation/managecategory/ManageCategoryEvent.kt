package com.android.memosnap.feature.dailytask.presentation.managecategory

sealed class ManageCategoryEvent {
    data class AddEditCategory(val category: String) : ManageCategoryEvent()
    data class UpdateAddCategoryPopup(val category: String) : ManageCategoryEvent()
    data class DeleteCategory(val categoryId: Int?) : ManageCategoryEvent()
    data class ChangeAddCategoryPopupVisibility(val isVisible: Boolean) : ManageCategoryEvent()
}