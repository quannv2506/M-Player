package com.project.mvvm.utilities

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle?.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getParcelable(key)) as? T
}

inline fun <reified T : Serializable> Bundle?.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getSerializable(key)) as? T
}

inline fun <reified T : Parcelable> Bundle?.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this?.getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") (this?.getParcelableArrayList(key))
}