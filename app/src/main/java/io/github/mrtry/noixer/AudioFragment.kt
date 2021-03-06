package io.github.mrtry.noixer


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.mrtry.noixer.databinding.FragmentAudioBinding
import kotlinx.android.synthetic.main.fragment_audio.view.*


class AudioFragment : Fragment() {
    lateinit var audioList: List<Audio>
    lateinit var binding: FragmentAudioBinding

    companion object {
        fun newInstance(audioList: List<Audio>): AudioFragment {
            return AudioFragment().apply {
                this.audioList = audioList
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentAudioBinding>(inflater, R.layout.fragment_audio, container, false)

        binding.root.audioList.apply {
            this.layoutManager = GridLayoutManager(activity, 2)
            this.setHasFixedSize(true)
            this.adapter = AudioAdapter(this@AudioFragment.audioList)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (binding.root.audioList.adapter as AudioAdapter).onDestroy()
    }
}
