package com.android.memosnap.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.memosnap.app.MemoSnapAppContent
import com.android.memosnap.ui.theme.MemoSnapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        // Make the app fullscreen
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        // Hide system bars
//        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }

        setContent {
            MemoSnapTheme {
                MemoSnapAppContent()
            }
        }
    }
}