package com.myapp.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.myapp.sunnyweather.logic.model.Place
import com.myapp.sunnyweather.util.context.SunnyWeatherApplication
import java.util.prefs.*
object PlaceDao {

    fun savePlace(place: Place){
        val editor=sharedPreferences().edit()
        editor.putString("place",Gson().toJson(place))
        editor.apply()
    }
    fun getSavedPlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isSaved()= sharedPreferences().contains("place")
    fun sharedPreferences()=SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}