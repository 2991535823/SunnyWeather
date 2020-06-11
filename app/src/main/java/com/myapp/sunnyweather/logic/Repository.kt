package com.myapp.sunnyweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.myapp.sunnyweather.logic.model.Place
import com.myapp.sunnyweather.logic.network.SunnyWeatherNetwork
import com.myapp.sunnyweather.util.log.LogUtil
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query:String)=liveData(Dispatchers.IO) {
        LogUtil.d("wrong",query)
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlace(query)
            LogUtil.d("wrong","${placeResponse.status}|${placeResponse.places[0].name}")
            if (placeResponse.status=="ok"){
                LogUtil.d("wrong",placeResponse.status)
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }

        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}
