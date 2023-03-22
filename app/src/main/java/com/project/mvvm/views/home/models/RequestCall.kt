package com.project.mvvm.views.home.models

import com.project.mvvm.utilities.commons.Constants

class RequestCall {
    var status = Constants.STOPPED
    var songs = mutableListOf<Song>()
}