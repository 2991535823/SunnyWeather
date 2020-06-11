package com.myapp.sunnyweather.logic.network

import com.myapp.sunnyweather.logic.model.PlaceResponse
import com.myapp.sunnyweather.util.context.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query")query: String):Call<PlaceResponse>
}