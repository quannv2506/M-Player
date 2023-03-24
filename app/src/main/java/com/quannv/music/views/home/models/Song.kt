package com.quannv.music.views.home.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Song: Parcelable {
    var id: String? = ""
    var albumId: Long ?= 0L
    var artist: String? = ""
    var title: String? = ""
    var mData: String? = ""
    var displayName: String? = ""
    var duration: String? = ""
    var isPlaying: Boolean = false
    override fun equals(other: Any?): Boolean {
        if (other !is Song) {
            return false
        }
        return id == other.id
    }

}