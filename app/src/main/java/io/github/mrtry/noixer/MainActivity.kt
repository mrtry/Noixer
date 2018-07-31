package io.github.mrtry.noixer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import io.github.mrtry.noixer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private val COLOR_LIST = intArrayOf(-0x832a56, -0xe195e, -0x133a6, -0x74a8, -0x16dbc0)
        private val VIEW_COUNT = COLOR_LIST.size
    }

    var service: AudioPlayerService? = null
    var isBound = false

    private val serviceIntent: Intent by lazy {
        Intent(this, AudioPlayerService::class.java)
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        iBinder: IBinder) {
            val binder = iBinder as AudioPlayerService.LocalBinder
            service = binder.service
            isBound = true

            val audioList = listOf(
                    Audio(R.raw.nc31909),
                    Audio(R.raw.nc2),
                    Audio(R.raw.nc3),
                    Audio(R.raw.nc4),
                    Audio(R.raw.nc5),
                    Audio(R.raw.nc6)
            )
            syncAudioState(audioList)

            val fragment = AudioFragment.newInstance(audioList)

            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(fragment)

            binding.viewPager.adapter = adapter
            binding.viewPager.addOnPageChangeListener(binding.indicatorView.positionSyncListener)
            binding.indicatorView.setup(VIEW_COUNT, R.drawable.indicator)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun syncAudioState(audioList: List<Audio>) {
        audioList.forEach {
            Log.d(MainActivity::class.java.simpleName, "${service?.playerMap?.containsKey(it.resId)}")
            if (service?.playerMap?.containsKey(it.resId) == true) {
                it.isPlaying = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}
