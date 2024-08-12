package com.android.memosnap.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.memosnap.app.MemoSnapApp
import com.android.memosnap.ui.theme.MemoSnapTheme

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MemoSnapTheme {
                MemoSnapApp()
            }
        }
    }
}