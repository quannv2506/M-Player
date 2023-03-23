package com.quannv.music.utilities

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Parcelable> Intent?.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getParcelableExtra(key)) as? T
}

inline fun <reified T : Serializable> Intent?.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getSerializableExtra(key)) as? T
}

inline fun <reified T : Parcelable> Intent?.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getParcelableArrayListExtra(key))
}