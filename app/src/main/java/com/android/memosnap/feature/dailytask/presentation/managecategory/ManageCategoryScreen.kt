package com.android.memosnap.feature.dailytask.presentation.managecategory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.memosnap.feature.dailytask.domain.model.Category
import com.android.memosnap.feature.dailytask.presentation.managecategory.components.TaskCategoryAppBar
import com.android.memosnap.feature.dailytask.presentation.managecategory.components.TaskCategoryItem
import com.android.memosnap.feature.dailytask.presentation.shared.component.AddCategoryPopup

@Composable
fun TaskCategoryScreen(
    navController: NavController,
    viewModel: ManageCategoryViewModel = hiltViewModel()
) {
    val categoriesState = viewModel.categoriesState.value
    val taskState = viewModel.tasksState.value
    val uiState = viewModel.uiState.value
    val editCategoryState = viewModel.editCategoryState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TaskCategoryAppBar(
            onClickBack = {
                navController.popBackStack()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 24.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categoriesState.categories.size) { index ->
                val category = categoriesState.categories[index]
                val taskCount = taskState.tasks.filter { it.categoryId == category.id }.size

                TaskCategoryItem(
                    categoryName = category.name,
                    taskCount = taskCount,
                    onDeleteClick = {
                        viewModel.onEvent(ManageCategoryEvent.DeleteCategory(category.id))
                    },
                    onEditClick = {
                        viewModel.onEvent(ManageCategoryEvent.ChangeAddCategoryPopupVisibility(true))
                        viewModel.onEvent(ManageCategoryEvent.UpdateAddCategoryPopup(category.name))
                        viewModel.onEvent(ManageCategoryEvent.UpdateEditCategory(category.id))
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .clickable {
                    viewModel.onEvent(ManageCategoryEvent.ChangeAddCategoryPopupVisibility(true))
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Create New",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (uiState.isAddCategoryPopupVisible) {
        AddCategoryPopup(
            categories = categoriesState.categories.map { it.name },
            onDismiss = {
                viewModel.onEvent(
                    ManageCategoryEvent.ChangeAddCategoryPopupVisibility(false)
                )
            },
            addNewCategory = {
                viewModel.onEvent(
                    ManageCategoryEvent.AddEditCategory(
                        category = Category(name = it, id = editCategoryState.id)
                    )
                )
                viewModel.onEvent(ManageCategoryEvent.UpdateAddCategoryPopup(""))
                viewModel.onEvent(ManageCategoryEvent.UpdateEditCategory(null))
            },
            value = editCategoryState.name
        )
    }
}