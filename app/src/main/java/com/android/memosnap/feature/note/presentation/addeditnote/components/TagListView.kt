package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.memosnap.feature.note.domain.model.NoteTag

@Composable
fun TagListView(tags: List<NoteTag>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tags.size) { index ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "# ${tags[index].name}",
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}
