package com.myapp.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status:String,val result:Result){
    data class Result(val realtime:Realtime)
    data class Realtime(val temperature:Float,val skycon:String,@SerializedName("air_quality")val airQuality:AirQuality)
    data class AirQuality(val aqi:AQI)
    data class AQI(val chn:Float)
    //数据模型定义在内部，可以防止同名冲突
}