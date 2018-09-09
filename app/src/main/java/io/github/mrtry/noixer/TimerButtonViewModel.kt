package io.github.mrtry.noixer

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View


/**
 * Created by mrtry on 2018/09/09.
 */
class TimerButtonViewModel(val icon: Drawable) : View.OnClickListener {

    private var timer: TimerUtil? = null
    private val label = arrayOf("1min", "3min", "5min", "10min", "15min", "20min", "30min")

    override fun onClick(view: View?) {
        view?.let {
            if (timer == null) {
                timer = TimerUtil(it.context)
            }

            when (timer!!.isProgress) {
                true -> {
                    AlertDialog.Builder(view.context).apply {
                        setTitle("Timer")
                        setMessage("Stop timer?")
                        setPositiveButton("Stop") { dialogInterface, i ->
                            timer!!.stopTimer()
                            icon.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
                        }
                        setCancelable(true)
                        setNegativeButton("Cancel", null)
                    }.show()
                }
                false -> {
                    AlertDialog.Builder(view.context).apply {
                        setTitle("Timer")
                        setItems(label) { dialogInterface: DialogInterface, i: Int ->
                            timer!!.startTimer(label[i].dropLast(3).toInt()) {
                                icon.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
                            }
                            icon.setColorFilter(ContextCompat.getColor(it.context, R.color.timer), PorterDuff.Mode.SRC_IN)
                        }
                        setCancelable(true)
                    }.show()
                }
            }
        }
    }
}
