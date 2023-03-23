package com.quannv.music.views.home

import com.quannv.music.models.FetchTokenApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface APIHome {

    @GET("/token/all-token-list")
    fun getList(): Observable<FetchTokenApiResponse>

}