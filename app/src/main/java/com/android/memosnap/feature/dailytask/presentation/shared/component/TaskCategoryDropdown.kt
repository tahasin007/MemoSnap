package com.android.memosnap.feature.dailytask.presentation.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskCategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    onClickAddNewCategory: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .width(150.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            color = if (selectedCategory == category) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onCategoryChange(category)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Dropdown item for creating a new category
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Create New",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                onClick = {
                    onClickAddNewCategory()
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        TextButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .25f)
            ),
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = selectedCategory,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}