package io.github.mrtry.noixer

/**
 * Created by mrtry on 2018/07/16.
 */
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationManagerCompat

private const val CHANNEL_ID = "io.github.mrtry.noixer.service"

@TargetApi(Build.VERSION_CODES.O)
enum class Channel(val id: String, private val channelNameRes: Int, private val importance: Int) {
    SERVICE(CHANNEL_ID, R.string.notification_message, NotificationManagerCompat.IMPORTANCE_DEFAULT);

    fun create(context: Context): NotificationChannel {
        val channelName = context.resources.getString(channelNameRes)
        return NotificationChannel(id, channelName, importance)
    }
}
