package com.android.memosnap.feature.note.presentation.addeditnote.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.memosnap.core.theme.BurntOrange
import com.android.memosnap.core.theme.CrimsonRed
import com.android.memosnap.core.theme.DeepBlue
import com.android.memosnap.core.theme.DodgerBlue
import com.android.memosnap.core.theme.ForestGreen
import com.android.memosnap.core.theme.GoldenYellow
import com.android.memosnap.core.theme.Lavender
import com.android.memosnap.core.theme.Scarlet
import com.android.memosnap.core.theme.SoftPink
import com.android.memosnap.core.theme.Teal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContainer(
    onDismiss: (Boolean) -> Unit,
    noteColor: Color,
    onColorChange: (Int) -> Unit,
    isBottomSheetOpen: Boolean = false
) {
    val bottomSheetState = rememberModalBottomSheetState()

    val colors: List<Color> = listOf(
        SoftPink,
        CrimsonRed,
        Scarlet,
        DodgerBlue,
        DeepBlue,
        GoldenYellow,
        ForestGreen,
        BurntOrange,
        Lavender,
        Teal
    )

    if (isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss(false) },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = {
                Spacer(modifier = Modifier)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Change Color",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(onClick = { onDismiss(false) }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        colors.take(5).forEach { color ->
                            ColorBox(
                                color = color,
                                isSelected = color == noteColor,
                                onClick = {
                                    onDismiss(false)
                                    onColorChange.invoke(color.toArgb())
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        colors.drop(5).forEach { color ->
                            ColorBox(
                                color = color,
                                isSelected = color == noteColor,
                                onClick = {
                                    onDismiss(false)
                                    onColorChange.invoke(color.toArgb())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}