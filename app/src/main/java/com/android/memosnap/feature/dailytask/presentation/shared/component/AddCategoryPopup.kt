package com.android.memosnap.feature.dailytask.presentation.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddCategoryPopup(
    categories: List<String>,
    onDismiss: () -> Unit,
    addNewCategory: (String) -> Unit,
    textSize: TextUnit = 16.sp,
    value: String = ""
) {
    var category by remember { mutableStateOf(value) }
    val maxCharLimit = 100 // Max character limit

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(onClick = onDismiss), // Handle outside click to dismiss
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // Prevent dismissing when clicking on the Card
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create new category",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        value = category,
                        onValueChange = {
                            // Limit the input to 100 characters
                            if (it.length <= maxCharLimit) {
                                category = it
                            }
                        },
                        textStyle = TextStyle.Default.copy(
                            fontSize = textSize,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f) // Ensure the text field takes up the majority of space
                                    ) {
                                        if (category.isEmpty()) {
                                            Text(
                                                text = "Input new category",
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                                fontSize = textSize
                                            )
                                        }
                                        innerTextField()
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Character counter inside the box at the bottom-right corner
                                    Text(
                                        text = "${category.length}/$maxCharLimit",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cancel and Save buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "Cancel")
                        }

                        TextButton(
                            onClick = {
                                onDismiss()
                                addNewCategory(category)
                            },
                            enabled = category.isNotEmpty() && categories.count { it == category } == 0
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}