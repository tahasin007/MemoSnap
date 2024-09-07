package com.android.memosnap.feature.dailytask.presentation

sealed class DailyTaskEvent {
    class ChangeBottomSheetVisibility(val isVisible: Boolean) : DailyTaskEvent()
    class EnteredTaskName(val name: String) : DailyTaskEvent()
    class EditedSubTask(val index: Int, val subTask: SubTask) : DailyTaskEvent()
    class RemoveSubTask(val index: Int) : DailyTaskEvent()
    data object AddSubTask : DailyTaskEvent()
    class SelectedCategory(val category: String) : DailyTaskEvent()
    class SelectedPriority(val priority: TaskPriority) : DailyTaskEvent()
}