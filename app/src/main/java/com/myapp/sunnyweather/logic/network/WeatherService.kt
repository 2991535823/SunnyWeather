package com.myapp.sunnyweather.logic.network

import com.myapp.sunnyweather.logic.model.DailyResponse
import com.myapp.sunnyweather.logic.model.RealtimeResponse
import com.myapp.sunnyweather.util.context.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String): Call<RealtimeResponse>
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,@Path("lat")lat:String): Call<DailyResponse>
}