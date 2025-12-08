package com.zip.lock.screen.wallpapers.work

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.WorkerParameters
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.presentation.ui.activity.HomeActivity

class NotifyLostConnectionWork(private var context: Context, params: WorkerParameters) :
    BaseWorker(context, params) {

    override var notificationId: Int = NOTIFY_LOST_CONNECTION

    override fun executeWork() {

    }

    override fun createNotification(): Notification {
        val title = "Lost Connection"
        val message = "You have lost connection to the VPN server, please check again"

        val intent = Intent(context, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra("notification_id", NOTIFY_LOST_CONNECTION)
        }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, flags)

        return notificationBuilder.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(message)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }.build()
    }

    companion object {
        const val NOTIFY_LOST_CONNECTION = 4
    }
}