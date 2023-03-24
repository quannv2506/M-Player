package com.quannv.music.models

import com.quannv.music.models.Song
import com.quannv.music.utilities.commons.Constants

class RequestCall {
    var status = Constants.STOPPED
    var songs = mutableListOf<Song>()
}