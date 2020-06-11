package com.myapp.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myapp.sunnyweather.logic.Repository
import com.myapp.sunnyweather.logic.dao.PlaceDao
import com.myapp.sunnyweather.logic.model.Location
import com.myapp.sunnyweather.logic.model.Place

class WeatherViewModel:ViewModel() {
    private val locationLiveData=MutableLiveData<Location>()
    var locationLng=""
    var locationLat=""
    var placeName=""

    val weatherLiveData=Transformations.switchMap(locationLiveData){
        input: Location ->  Repository.refreshWeather(input.lng,input.lat)
    }
    fun refreshWeather(location: Location){
        locationLiveData.value=location
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value= Location(lng,lat)
    }
}