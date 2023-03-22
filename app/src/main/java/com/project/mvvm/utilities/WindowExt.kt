package com.project.mvvm.utilities

import android.content.Context
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.changeStatusBarColor(context: Context, color: Int) {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    statusBarColor = context.resources.getColor(color)
}

fun Window.setFullScreen() {
    WindowCompat.setDecorFitsSystemWindows(this, true)
}

fun Window.lightStatusBar(isLight: Boolean = true) {
    val wic = WindowInsetsControllerCompat(this, this.decorView)
    wic.isAppearanceLightStatusBars = isLight
}