package com.quannv.music.views.home.models

import com.google.gson.annotations.SerializedName
import com.quannv.music.bases.BaseResponse

class SplashResponse : BaseResponse<SplashModel>()

class SplashModel {
    @SerializedName("token")
    var token: String? = null

}