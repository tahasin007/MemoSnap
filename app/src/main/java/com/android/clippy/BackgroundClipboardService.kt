package com.android.clippy

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BackgroundClipboardService : Service() {

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): BackgroundClipboardService = this@BackgroundClipboardService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize your clipboard monitoring here
        Log.i(TAG, "Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")
    }

    companion object {
        private const val TAG = "BackgroundClipboardService"
    }
}

