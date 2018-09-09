package io.github.mrtry.noixer

import android.content.Context
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by mrtry on 2017/06/14.
 */

class TimerUtil(val context: Context) {
    private var timer: Timer? = null
    var isProgress = false

    fun startTimer(minutes: Int, callback: () -> Unit) {
        var counter = minutes * 60
        timer = Timer()

        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (counter > 0) {
                    counter--
                } else {
                    cancel()
                    timer = null

                    EventBus.getDefault().post(MuteAudioEvent())
                    callback()
                }
            }
        }, 0, 1000)
        isProgress = true
    }

    fun stopTimer() {
        timer?.cancel()
        isProgress = false
    }
}
