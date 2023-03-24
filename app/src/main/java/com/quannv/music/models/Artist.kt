package com.quannv.music.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    var id: Long? = 0L,
    var artist: String? = "",
    var numberOfAlbums: Int? = 0,
    var numberOfSongs: Int? = 0,
    var artistImage: Int? = 0,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is Artist) {
            return false
        }
        return id == other.id
    }

}