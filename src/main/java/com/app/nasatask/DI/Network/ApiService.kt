package com.app.nasatask.DI.Network

import com.app.nasatask.Models.Apod

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("planetary/apod")
    fun apod(@Query("api_key") api_key :String, @Query("date") date :String): Single<Apod>
}
