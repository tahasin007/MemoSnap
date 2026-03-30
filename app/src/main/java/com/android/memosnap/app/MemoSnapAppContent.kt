package com.android.memosnap.app

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.android.memosnap.core.component.DrawerMenuContent
import com.android.memosnap.core.navigation.MyAppNavHost

@Composable
fun MemoSnapAppContent() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val view = LocalView.current
    val context = LocalContext.current
    val surface = MaterialTheme.colorScheme.surface
    val useDarkIcons = surface.luminance() > 0.5f
    val activity = remember(context) { context.findActivity() }

    SideEffect {
        activity?.window?.let { window ->
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = useDarkIcons
                isAppearanceLightNavigationBars = useDarkIcons
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenuContent { _ ->
                // Handle menu item clicks if needed
            }
        },
        content = {
            Scaffold { _ ->
                Box(modifier = Modifier.fillMaxSize()) {
                    MyAppNavHost(navController = navController)
                }
            }
        }
    )
}

private tailrec fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
