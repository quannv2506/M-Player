package com.project.mvvm.utilities

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(@StringRes textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, duration).show()
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, duration).show()
}

fun Fragment.toast(@StringRes textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), textRes, duration).show()
}