package io.github.mrtry.noixer

import android.arch.lifecycle.MutableLiveData
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.SeekBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by mrtry on 2018/07/15.
 */
class AudioViewModel(audio: Audio) : View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    val audioObservable = MutableLiveData<Audio>()

    init {
        audioObservable.value = audio
        EventBus.getDefault().register(this)
    }

    private fun currentState(): Audio = audioObservable.value!!

    private fun updateAudioState(toPlay: Boolean = !currentState().isPlaying) {
        audioObservable.value = currentState().copy(isPlaying = toPlay)
        currentState().icon.setColorFilter(if (currentState().isPlaying) currentState().iconColor else Color.LTGRAY, PorterDuff.Mode.SRC_IN)
        EventBus.getDefault().post(AudioUpdateEvent(currentState()))
    }

    override fun onClick(p0: View?) {
        updateAudioState()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        audioObservable.value = currentState().copy(volume = progress.toFloat() / 100)
        EventBus.getDefault().post(AudioUpdateEvent(currentState()))
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    fun onDestroy() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MuteAudioEvent) {
        updateAudioState(false)
    }
}