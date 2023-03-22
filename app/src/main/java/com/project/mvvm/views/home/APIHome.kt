package com.project.mvvm.views.home

import com.project.mvvm.models.FetchTokenApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface APIHome {

    @GET("/token/all-token-list")
    fun getList(): Observable<FetchTokenApiResponse>

}