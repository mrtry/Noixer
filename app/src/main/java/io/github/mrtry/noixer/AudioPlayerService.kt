package io.github.mrtry.noixer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class AudioPlayerService : Service() {
    private val binder = LocalBinder()
    val playerMap = mutableMapOf<Int, MediaPlayer>()

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        playerMap.values.map { it.stop() }
    }

    private fun stop(player: MediaPlayer) {
        player.apply {
            stop()
            reset()
            release()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: AudioUpdateEvent) {

        when (event.audio.isPlaying) {
            true -> {
                if (!playerMap.containsKey(event.audio.audioResId)) {
                    val player = MediaPlayer.create(this, event.audio.audioResId)

                    player.isLooping = true
                    player.start()

                    if (playerMap.isEmpty()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(AudioPlayerService.createIntent(this))
                        } else {
                            startService(AudioPlayerService.createIntent(this))
                        }

                        startForeground(1, NotificationHelper(this).createNotification())
                    }

                    playerMap[event.audio.audioResId] = player
                }

                playerMap[event.audio.audioResId]?.setVolume(event.audio.volume, event.audio.volume)
            }
            false -> {
                val player = playerMap[event.audio.audioResId]

                player?.let {
                    stop(it)
                    playerMap.remove(event.audio.audioResId)

                    if (playerMap.isEmpty()) {
                        stopForeground(true)
                        stopService(AudioPlayerService.createIntent(this))
                    }
                }
            }
        }
    }

    companion object {
        val TAG = AudioPlayerService::class.java.toString()

        fun createIntent(context: Context): Intent = Intent(context, AudioPlayerService::class.java)
    }

    inner class LocalBinder : Binder() {
        val service: AudioPlayerService
            get() = this@AudioPlayerService
    }

}
