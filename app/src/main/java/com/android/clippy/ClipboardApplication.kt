package com.android.clippy

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder

class ClipboardApplication : Application() {

    private lateinit var backgroundServiceConnection: ServiceConnection
    private lateinit var foregroundServiceIntent: Intent

    override fun onCreate() {
        super.onCreate()

        // Initialize the foreground service intent
        foregroundServiceIntent = Intent(this, ForegroundClipboardService::class.java)

        // Register activity lifecycle callbacks
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityStarted(activity: Activity) {
                // App is entering the foreground, stop the foreground service and start the background service
                stopService(foregroundServiceIntent)

                // Bind the background service
                val backgroundServiceIntent = Intent(this@ClipboardApplication, BackgroundClipboardService::class.java)
                backgroundServiceConnection = object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        // Handle service connection
                    }

                    override fun onServiceDisconnected(name: ComponentName) {
                        // Handle service disconnection
                    }
                }
                bindService(backgroundServiceIntent, backgroundServiceConnection, Context.BIND_AUTO_CREATE)
            }

            override fun onActivityStopped(activity: Activity) {
                // App is going into the background, start the foreground service and stop the background service
                startService(foregroundServiceIntent)

                // Unbind the background service
                unbindService(backgroundServiceConnection)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}

