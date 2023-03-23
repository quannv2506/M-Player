package com.quannv.music.utilities

import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

infix fun TextView.withTextOrGone(text: CharSequence?) {
    if (text.isNullOrEmpty()) {
        isVisible = false
        this.text = emptyString()
    } else {
        isVisible = true
        this.text = text
    }
}

infix fun TextView.withTextOrInvisible(text: CharSequence?) {
    if (text.isNullOrEmpty()) {
        isInvisible = true
        this.text = emptyString()
    } else {
        isInvisible = false
        this.text = text
    }
}

fun TextView.setTextColorRes(@ColorRes color: Int) {
    setTextColor(context.getColor(color))
}

fun TextView.underline(underline: String) {
    try {

        val spannableString = SpannableString(text)

        spannableString.setSpan(
            UnderlineSpan(),
            spannableString.indexOf(underline),
            spannableString.indexOf(underline) + underline.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        setText(spannableString, TextView.BufferType.SPANNABLE)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun TextView.getTextWithTrim(): String =
    this.text.toString().trim()
