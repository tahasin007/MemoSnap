package com.android.memosnap.data.sample

import com.android.memosnap.Note

val sampleNotes = List(12) { index ->
    Note(
        id = index,
        title = "Note $index",
        content = if (index % 2 == 0) "This is the content of note number $index. It can be of variable length."
        else "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
    )
}