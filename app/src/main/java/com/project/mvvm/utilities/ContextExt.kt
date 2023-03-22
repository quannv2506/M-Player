package com.project.mvvm.utilities

import android.content.*
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.project.mvvm.BuildConfig
import com.project.mvvm.R
import java.io.File

fun Context.isConnected(): Boolean {
    return try {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isAvailable
                && connectivityManager.activeNetworkInfo!!.isConnected)
    } catch (ex: Exception) {
        false
    }
}

fun Context.copyToClipBoard(content: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(getString(R.string.app_name), content)
    clipboard.setPrimaryClip(clip)
}

fun Context.getClipboardText(trimmed: Boolean = true): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val text = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
    return if (trimmed) text?.trim() else text
}

fun Context.shareText(value: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, value)
    startActivity(Intent.createChooser(shareIntent, "Share Text"))
}

fun Context.shareScreenShot(image: File, providedText: String = "Save Screenshot") {
    val uri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", image)

    val intent = ShareCompat.IntentBuilder(this).intent.apply {
        action = Intent.ACTION_SEND
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        type = "image/*"
        putExtra(Intent.EXTRA_TEXT, providedText)
        putExtra(Intent.EXTRA_STREAM, uri)
    }
    try {
        startActivity(Intent.createChooser(intent, "Share with"))
    } catch (e: ActivityNotFoundException) {
        toast("No App Available")
    }
}

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableId)
}

fun Context.getColorStateListCompat(@ColorRes colorRes: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, colorRes)
}