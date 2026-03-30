package com.android.memosnap.core.screens

sealed class Screen(val route: String, val label: String) {
    data object AddEditNote : Screen("note_editor", "NoteEditor")
    data object ArchivedNotes : Screen("archived_notes", "ArchivedNotes")
    data object Home : Screen("home", "Home")
    data object Search : Screen("search", "Search")
}
