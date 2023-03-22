package com.project.mvvm.views.home.models

import com.google.gson.annotations.SerializedName
import com.project.mvvm.bases.BaseResponse

class SplashResponse : BaseResponse<SplashModel>()

class SplashModel {
    @SerializedName("token")
    var token: String? = null

}