package com.quannv.music.views.home.models

import com.quannv.music.utilities.commons.Constants

class RequestCall {
    var status = Constants.STOPPED
    var songs = mutableListOf<Song>()
}