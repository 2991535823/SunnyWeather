package com.myapp.sunnyweather.util.context

import android.app.Application
import android.content.Context
import com.myapp.sunnyweather.util.Toast.showToast

class SunnyWeatherApplication:Application() {
    companion object{
        lateinit var context: Context
        const val TOKEN="7xoSm4k7GIK8X8E1"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

    override fun onLowMemory() {
        super.onLowMemory()
        "LowMemory".showToast()
    }
}