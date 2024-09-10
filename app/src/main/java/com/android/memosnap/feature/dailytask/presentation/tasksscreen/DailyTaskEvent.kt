package com.android.memosnap.feature.dailytask.presentation.tasksscreen

import com.android.memosnap.feature.dailytask.domain.model.SubTask

sealed class DailyTaskEvent {
    class ChangeBottomSheetVisibility(val isVisible: Boolean) : DailyTaskEvent()
    class ChangeAddCategoryPopupVisibility(val isVisible: Boolean) : DailyTaskEvent()
    class EnteredTaskName(val name: String) : DailyTaskEvent()
    class EditedSubTask(val index: Int, val subTask: SubTask) : DailyTaskEvent()
    class RemoveSubTask(val index: Int) : DailyTaskEvent()
    data object AddSubTask : DailyTaskEvent()
    class SelectedCategory(val category: String) : DailyTaskEvent()
    class SelectedPriority(val priority: TaskPriority) : DailyTaskEvent()
    class AddCategory(val category: String) : DailyTaskEvent()
    data object SaveTask : DailyTaskEvent()
    class LoadTasksByCategory(val category: String?) : DailyTaskEvent()
}