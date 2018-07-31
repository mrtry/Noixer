package io.github.mrtry.noixer

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.mrtry.noixer.databinding.ListRowBinding


/**
 * Created by mrtry on 2018/07/15.
 */
class AudioAdapter(private val audioList: List<Audio>): RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ListRowBinding>(LayoutInflater.from(parent.context), R.layout.list_row, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AudioAdapter.ViewHolder, position: Int) {
        val viewModel = AudioViewModel(audioList[position])
        holder.binding.viewModel = viewModel
        holder.binding.seekBar.progress = (viewModel.audioObservable.value!!.volume * 100).toInt()
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    class ViewHolder(val binding: ListRowBinding) : RecyclerView.ViewHolder(binding.root)
}