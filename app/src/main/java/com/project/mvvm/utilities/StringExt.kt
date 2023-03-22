package com.project.mvvm.utilities

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.Patterns
import com.project.mvvm.BuildConfig
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun String.openLink(context: Context) {
    var url = this
    if (!url.startsWith("http://") && !url.startsWith("https://"))
        url = "http://$url"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
    context.startActivity(browserIntent);
}

fun String?.isValidEmail(): Boolean {
    return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isValidPhone(): Boolean {
    return !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()
}

/**
 * Return value of string array on android xml file
 */
fun String.getStringValue(): String {
    return try {
        val resource = Resources.getSystem()
        val resId: Int = resource.getIdentifier(this, "string", BuildConfig.APPLICATION_ID)
        resource.getString(resId)
    } catch (ex: Exception) {
        ""
    }
}

fun emptyString() = ""

fun StringBuilder.appendBreakLine() {
    append("\n")
}

fun String.ellipsizeAddress() = take(4) + "…" + takeLast(4)

fun String.removeWhiteSpaces(): String = replace(" ", emptyString())

fun String?.toBigIntSafe(): BigInteger {
    if (isNullOrEmpty()) return BigInteger.ZERO
    return try {
        BigInteger(this.removePrefix("0x"), 16)
    } catch (ex: java.lang.NumberFormatException) {
        ex.printStackTrace()
        BigInteger.ZERO
    }
}

fun String?.toBigIntegerOrDefaultZero(): BigInteger {
    if (this.isNullOrEmpty()) return BigInteger.ZERO
    return try {
        val format = getKSNumberFormat().parse(this)?.toString()
        BigInteger(format)
    } catch (ex: Exception) {
        ex.printStackTrace()
        BigInteger.ZERO
    }
}

fun getKSNumberFormat(): NumberFormat {
    return DecimalFormat.getInstance(Locale.US).setKSFractionDigits()
}

fun NumberFormat.setKSFractionDigits(): NumberFormat {
    this.maximumFractionDigits = 18
    return this
}