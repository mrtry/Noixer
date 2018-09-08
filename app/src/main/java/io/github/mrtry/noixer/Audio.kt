package io.github.mrtry.noixer

import android.content.res.ColorStateList


/**
 * Created by mrtry on 2018/07/15.
 */
data class Audio(val audioResId: Int, val iconResId: Int, val tint: ColorStateList, var isPlaying: Boolean = false, var volume: Float = 1.0F)
