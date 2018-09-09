package io.github.mrtry.noixer

import android.arch.lifecycle.MutableLiveData
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.SeekBar
import org.greenrobot.eventbus.EventBus


/**
 * Created by mrtry on 2018/07/15.
 */
class AudioViewModel(audio: Audio) : View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    val audioObservable = MutableLiveData<Audio>()

    init {
        audioObservable.value = audio
    }

    private fun currentState(): Audio = audioObservable.value!!

    override fun onClick(p0: View?) {
        audioObservable.value = currentState().copy(isPlaying = !currentState().isPlaying)
        EventBus.getDefault().post(AudioUpdateEvent(currentState()))

        currentState().icon.setColorFilter(if (currentState().isPlaying) currentState().iconColor else Color.LTGRAY, PorterDuff.Mode.SRC_IN)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        audioObservable.value = currentState().copy(volume = progress.toFloat() / 100)
        EventBus.getDefault().post(AudioUpdateEvent(currentState()))
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}