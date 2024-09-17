package com.android.memosnap.feature.dailytask.presentation.edittask

import com.android.memosnap.feature.dailytask.domain.model.SubTask
import com.android.memosnap.feature.dailytask.presentation.tasksscreen.TaskPriority

sealed class EditTaskEvent {
    class ChangeTaskName(val name: String) : EditTaskEvent()
    class ChangeCategory(val category: String) : EditTaskEvent()
    class ChangePriority(val priority: TaskPriority) : EditTaskEvent()
    class AddCategory(val category: String) : EditTaskEvent()
    data object AddSubTask : EditTaskEvent()
    class EditSubTask(val index: Int, val subTask: SubTask) : EditTaskEvent()
    class RemoveSubTask(val index: Int) : EditTaskEvent()
    class ChangeTaskNote(val note: String) : EditTaskEvent()
    data object SaveTask : EditTaskEvent()
    data object DeleteTask : EditTaskEvent()
    class ChangeAddCategoryPopupVisibility(val isVisible: Boolean) : EditTaskEvent()
    class SelectedCategory(val category: String) : EditTaskEvent()
}