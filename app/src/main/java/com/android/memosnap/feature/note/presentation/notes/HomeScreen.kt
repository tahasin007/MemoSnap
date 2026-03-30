package com.android.memosnap.feature.note.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.memosnap.core.component.EmptyNoteView
import com.android.memosnap.core.screens.Screen
import com.android.memosnap.feature.note.domain.model.NoteType
import com.android.memosnap.feature.note.presentation.notes.components.HomeScreenAppBar
import com.android.memosnap.feature.note.presentation.notes.components.NoteCard
import java.util.Calendar

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteState = viewModel.state.value
    val hasNotes = noteState.notes.isNotEmpty()
    val hasActiveFilters = noteState.categoryFilter != null || noteState.searchQuery.isNotBlank()
    val onCreateNoteClick = {
        navController.navigate(
            Screen.AddEditNote.route +
                    "?noteType=${NoteType.REGULAR.name}&createMode=NOTE"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HomeScreenAppBar(
            onArchiveClick = { navController.navigate(Screen.ArchivedNotes.route) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchNotesField(
                query = noteState.searchQuery,
                onQueryChange = { viewModel.onEvent(NotesEvent.SetSearchQuery(it)) },
                onClear = { viewModel.onEvent(NotesEvent.SetSearchQuery("")) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabChipShape = RoundedCornerShape(24.dp)

                item {
                    FilterChip(
                        selected = !hasActiveFilters,
                        onClick = { viewModel.onEvent(NotesEvent.ClearFilters) },
                        label = { Text("All") },
                        shape = tabChipShape,
                        colors = homeFilterChipColors(),
                        border = homeFilterChipBorder(selected = !hasActiveFilters)
                    )
                }
                items(
                    noteState.categories,
                    key = { it.id ?: it.name.hashCode() }
                ) { category ->
                    FilterChip(
                        selected = noteState.categoryFilter == category.name,
                        onClick = {
                            viewModel.onEvent(
                                NotesEvent.SetCategoryFilter(
                                    if (noteState.categoryFilter == category.name) null else category.name
                                )
                            )
                        },
                        label = { Text(category.name) },
                        shape = tabChipShape,
                        colors = homeFilterChipColors(),
                        border = homeFilterChipBorder(
                            selected = noteState.categoryFilter == category.name
                        )
                    )
                }
            }

            if (!hasNotes) {
                EmptyNoteView(
                    title = if (hasActiveFilters) "No matching notes" else "No notes yet",
                    description = if (hasActiveFilters) {
                        "Try clearing search or filters."
                    } else {
                        "Capture your first thought and keep it organized."
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            } else {
                Column(modifier = Modifier.weight(1f)) {

                    if (!noteState.smartSectionsEnabled) {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 8.dp
                        ) {
                            items(noteState.notes.size) { index ->
                                val note = noteState.notes[index]
                                val noteId = note.id
                                val tags =
                                    if (noteId == null) emptyList() else noteState.tagsByNoteId[noteId].orEmpty()
                                val progress =
                                    if (noteId == null) null else noteState.checklistProgressByNoteId[noteId]

                                NoteCard(
                                    note = note,
                                    categoryName = null,
                                    tags = tags,
                                    checklistProgress = progress,
                                    onToggleCompletion = { checked ->
                                        if (noteId != null) {
                                            viewModel.onEvent(
                                                NotesEvent.ToggleCompletion(
                                                    noteId,
                                                    checked
                                                )
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }) {
                                            navController.navigate(
                                                Screen.AddEditNote.route +
                                                        "?noteId=${note.id}"
                                            )
                                        }
                                )
                            }
                        }
                    } else {
                        val sectionOrder =
                            listOf("Pinned", "Today", "Upcoming", "Overdue", "All Notes")
                        val grouped = noteState.notes.groupBy { note ->
                            when {
                                note.isPinned -> "Pinned"
                                note.dueAt == null -> "All Notes"
                                isSameDay(note.dueAt, System.currentTimeMillis()) -> "Today"
                                note.dueAt < startOfTodayMillis() -> "Overdue"
                                else -> "Upcoming"
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            sectionOrder.forEach { section ->
                                val notes = grouped[section].orEmpty()
                                if (notes.isNotEmpty()) {
                                    item {
                                        Text(
                                            text = section,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(
                                                horizontal = 4.dp,
                                                vertical = 4.dp
                                            )
                                        )
                                    }

                                    items(notes, key = { it.id ?: it.hashCode() }) { note ->
                                        val noteId = note.id
                                        val tags =
                                            if (noteId == null) emptyList() else noteState.tagsByNoteId[noteId].orEmpty()
                                        val progress =
                                            if (noteId == null) null else noteState.checklistProgressByNoteId[noteId]

                                        NoteCard(
                                            note = note,
                                            categoryName = null,
                                            tags = tags,
                                            checklistProgress = progress,
                                            onToggleCompletion = { checked ->
                                                if (noteId != null) {
                                                    viewModel.onEvent(
                                                        NotesEvent.ToggleCompletion(
                                                            noteId,
                                                            checked
                                                        )
                                                    )
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(
                                                    indication = null,
                                                    interactionSource = remember { MutableInteractionSource() }
                                                ) {
                                                    navController.navigate(Screen.AddEditNote.route + "?noteId=${note.id}")
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }

            CreateNoteButton(
                onClick = onCreateNoteClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
private fun CreateNoteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Create note")
    }
}

private fun startOfTodayMillis(): Long {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

private fun isSameDay(timeA: Long, timeB: Long): Boolean {
    val a = Calendar.getInstance().apply { timeInMillis = timeA }
    val b = Calendar.getInstance().apply { timeInMillis = timeB }
    return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
            a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)
}

@Composable
private fun SearchNotesField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        placeholder = { Text("Search notes or #tag") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable(onClick = onClear)
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.28f),
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

@Composable
private fun homeFilterChipColors() = FilterChipDefaults.filterChipColors(
    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
    labelColor = MaterialTheme.colorScheme.primary,
    iconColor = MaterialTheme.colorScheme.primary,
    selectedContainerColor = MaterialTheme.colorScheme.primary,
    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
)

@Composable
private fun homeFilterChipBorder(
    selected: Boolean,
    enabled: Boolean = true
) = FilterChipDefaults.filterChipBorder(
    enabled = enabled,
    selected = selected,
    borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.28f),
    selectedBorderColor = MaterialTheme.colorScheme.primary
)

