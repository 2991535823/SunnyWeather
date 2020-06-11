package com.myapp.sunnyweather.util.Toast

import android.widget.Toast
import com.myapp.sunnyweather.util.context.SunnyWeatherApplication

fun String.showToast(duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(SunnyWeatherApplication.context,this,duration).show()
}