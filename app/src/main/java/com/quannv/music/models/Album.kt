package com.quannv.music.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    var id: Long? = 0L,
    var album: String? = "",
    var artist: String? = "",
    var numberOfSongs: Int? = 0,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is Album) {
            return false
        }
        return id == other.id
    }

}