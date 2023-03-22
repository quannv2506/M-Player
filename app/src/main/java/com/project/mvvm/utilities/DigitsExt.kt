package com.project.mvvm.utilities

import android.content.Context
import android.util.DisplayMetrics

fun Int.convertPxToDp(): Int {
    return this * Utils.shared.dpiDevice / 160
}

fun Float.convertPxToDp(): Float {
    return this * Utils.shared.dpiDevice / 160.0f
}

fun Int.convertDpToPx(): Int {
    return this * 160 / Utils.shared.dpiDevice
}

fun Float.convertDpToPx(): Float {
    return 1.0f * (this * 160.0f / Utils.shared.dpiDevice)
}

fun Int.dpToPx(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return this * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.pxToDp(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return this / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}