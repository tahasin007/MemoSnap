package com.android.clippy

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundClipboardService : Service() {

    private val CHANNEL_ID = "clipboard_service_channel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, getNotification())
        // Initialize your clipboard monitoring here
        Log.d(TAG, "Service created")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Service logic here
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Clipboard Monitoring")
            .setContentText("Monitoring clipboard in background")
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .build()
    }

    private fun createNotificationChannel() {
        val name = "Clipboard Service"
        val descriptionText = "Notification channel for clipboard monitoring service"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TAG = "ForegroundClipboardService"
    }
}
