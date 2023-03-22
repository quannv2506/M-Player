package com.project.mvvm.views.home.models

class Song {
    var id: String? = ""
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