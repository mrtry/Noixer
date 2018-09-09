package io.github.mrtry.noixer

import android.view.View
import org.greenrobot.eventbus.EventBus


/**
 * Created by mrtry on 2018/09/09.
 */
class MuteButtonViewModel : View.OnClickListener {
    override fun onClick(p0: View?) {
        EventBus.getDefault().post(MuteButtonClickedEvent())
    }
}