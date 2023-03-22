package com.project.mvvm.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageViewBindingAdapter {

    @BindingAdapter("app:identifier")
    @JvmStatic
    fun loadResource(view: ImageView, identifier: String?) {
        view.load(identifier)
    }
}