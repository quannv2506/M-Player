package com.quannv.music.utilities

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.quannv.music.R

infix fun ImageView.withImageOrGone(@DrawableRes imageRes: Int?) {
    if (imageRes == null || imageRes == 0) {
        isVisible = false
    } else {
        isVisible = true
        setImageResource(imageRes)
    }
}

infix fun ImageView.withImageResource(@DrawableRes imageRes: Int?) {
    if (imageRes == null || imageRes == 0) return
    setImageResource(imageRes)
}

fun ImageView.load(url: String?){
    if (url.isNullOrBlank()) this.setImageResource(R.drawable.ic_placeholder_default)
    try {
        Glide.with(this)
            .load(url).into(this)
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}
