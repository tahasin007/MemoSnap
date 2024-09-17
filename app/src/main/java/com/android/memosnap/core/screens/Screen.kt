package com.android.memosnap.core.screens

sealed class Screen(val route: String, val label: String) {
    data object AddEditNote : Screen("AddEditNote", "AddEditNote")
    data object ArchivedNotes : Screen("archived_notes", "ArchivedNotes")
    data object Home : Screen("home", "Home")
    data object Search : Screen("search", "Search")
    data object NoteTags : Screen("note_tags", "NoteTags")
    data object NotesByTags : Screen("notes_by_tags", "NotesByTags")

    data object DailyTask : Screen("daily_task", "DailyTask")
    data object EditTask : Screen("edit_task", "EditTask")
    data object AddNotesToTask : Screen("add_notes_to_task", "AddNotesToTask")
    data object TaskCategory : Screen("task_category", "TaskCategory")
}
