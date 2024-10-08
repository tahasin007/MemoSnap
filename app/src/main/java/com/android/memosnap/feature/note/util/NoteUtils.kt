package com.android.memosnap.feature.note.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object NoteUtils {

    fun getCurrentFormattedDate(): String {
        // Get the current date
        val currentDate = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH)
        return dateFormat.format(currentDate)
    }

    fun getTotalCharacters(body: String): Int {
        return body.replace("\\s".toRegex(), "").length
    }
}