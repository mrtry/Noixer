package io.github.mrtry.noixer

import android.graphics.drawable.Drawable


/**
 * Created by mrtry on 2018/07/15.
 */
data class Audio(val audioResId: Int, val icon: Drawable, val iconColor: Int, var isPlaying: Boolean = false, var volume: Float = 1.0F)
