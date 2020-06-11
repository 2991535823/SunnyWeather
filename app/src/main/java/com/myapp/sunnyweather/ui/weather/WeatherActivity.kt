package com.myapp.sunnyweather.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myapp.sunnyweather.R
import com.myapp.sunnyweather.logic.model.Weather
import com.myapp.sunnyweather.logic.model.getSky
import com.myapp.sunnyweather.ui.place.WeatherViewModel
import com.myapp.sunnyweather.util.Toast.showToast
import com.myapp.sunnyweather.util.log.LogUtil.d
import com.myapp.sunnyweather.util.log.LogUtil.v
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather)
        val decorView=window.decorView
        decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor=Color.TRANSPARENT
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat=intent.getStringExtra("location_lat")?:""
        }
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if (weather!=null){
                showWeather(weather)
            }else{
                "无法获取天气QWQ".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
    }
    private fun showWeather(weather: Weather){
        placeName.text=viewModel.placeName
        val realtime=weather.realtime
        val daily=weather.daily
        val currentTempText="${realtime.temperature.toInt()}℃"
        currentTemp.text=currentTempText
        currentSky.text= getSky(realtime.skycon)!!.info
        val currentPM25Text="空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text=currentPM25Text
        now_Layout.setBackgroundResource(getSky(realtime.skycon)!!.bg)
        forecastLayout.removeAllViews()
        val days=daily.skycon.size
        for (i in 1 until days){
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]
            val view=LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
            val dateInfo=view.findViewById<TextView>(R.id.dateInfo)
            val skyInfo=view.findViewById<TextView>(R.id.skyInfo)
            val skyIcon=view.findViewById<ImageView>(R.id.skyIcon)
            val temperatureInfo=view.findViewById<TextView>(R.id.temperatureInfo)
            dateInfo.text = skycon.date.subSequence(0,10)
            val sky= getSky(skycon.value)!!
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min.toInt()}~${temperature.max.toInt()}℃"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc

        weatherLayout.visibility= View.VISIBLE
    }
}
