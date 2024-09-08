package com.android.memosnap.feature.dailytask.data

import androidx.room.TypeConverter
import com.android.memosnap.feature.dailytask.domain.model.SubTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromSubTasks(subTasks: List<SubTask>?): String? {
        return gson.toJson(subTasks)
    }

    @TypeConverter
    fun toSubTasks(subTasksString: String?): List<SubTask>? {
        val listType = object : TypeToken<List<SubTask>>() {}.type
        return gson.fromJson(subTasksString, listType)
    }
}