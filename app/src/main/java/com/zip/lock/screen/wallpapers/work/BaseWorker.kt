package com.zip.lock.screen.wallpapers.work

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

abstract class BaseWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    val notificationBuilder = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)

    init {
        createChannel()
    }

    override suspend fun doWork(): Result {
        try {
            executeWork()
            showNotification(createNotification())
            return Result.success()
        } catch (_: Exception) {
            return Result.failure()
        }
    }

    abstract fun executeWork()

    abstract fun createNotification(): Notification

    abstract var notificationId: Int

    private fun createChannel() {
        val channelName = "Channel Name"
        val channelDescription = "Channel Description"
        val channelImportance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, channelImportance).apply {
            description = channelDescription
        }
        applicationContext.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun showNotification(
        notification: Notification
    ) {
        if (ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "app_channel"
    }

    fun removeNotification(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }
}