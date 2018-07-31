package io.github.mrtry.noixer

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat


/**
 * Created by mrtry on 2018/07/16.
 */
class NotificationHelper(val context: Context) {
    fun createNotification():Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelIfNeeded()
        }

        return NotificationCompat.Builder(context, Channel.SERVICE.id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(createIntent())
                .setContentTitle(context.getString(R.string.notification_message))
                .setContentText(context.getString(R.string.notification_message))
                .build()
    }

    fun createIntent(): PendingIntent
            = PendingIntent.getActivity(context, 1, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannelIfNeeded() {
        val channel = Channel.SERVICE.create(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}