package com.android.memosnap.feature.note.presentation.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.memosnap.R
import com.android.memosnap.feature.note.presentation.tags.components.NoteTagsAppBar
import com.android.memosnap.ui.component.EmptyNoteView
import com.android.memosnap.ui.screens.Screen

@Composable
fun NoteTagScreen(
    navController: NavController,
    viewModel: NoteTagsViewModel = hiltViewModel()
) {
    val noteTags = viewModel.state.value
    val uiState = viewModel.uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        NoteTagsAppBar(
            onClickBack = { navController.popBackStack() },
            onClickAdd = {},
            addTag = { viewModel.onEvent(NoteTagsEvent.AddNewTag(it)) },
            deleteTag = { viewModel.onEvent(NoteTagsEvent.DeleteTag(it)) },
            noteTags = noteTags.tags,
            isAddTagPopupVisible = uiState.isAddTagPopupVisible
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (noteTags.tags.isEmpty()) {
                EmptyNoteView(
                    title = "No Tags Found",
                    description = "Add a new tag to organize your notes!",
                    drawableId = R.drawable.ic_tag,
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(noteTags.tags.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.NotesByTags.route +
                                                "?tagId=${noteTags.tags[index].id}"
                                    )
                                }
                                .padding(horizontal = 8.dp)
                                .shadow(2.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = noteTags.tags[index].name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.surface
                                    ),
                                    modifier = Modifier.weight(1f) // Takes up as much space as possible
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = "Navigate to Notes by Tag",
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}