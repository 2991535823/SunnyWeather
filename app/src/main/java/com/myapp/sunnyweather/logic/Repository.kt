package com.myapp.sunnyweather.logic

import androidx.lifecycle.liveData
import com.myapp.sunnyweather.logic.dao.PlaceDao
import com.myapp.sunnyweather.logic.model.Place
import com.myapp.sunnyweather.logic.model.Weather
import com.myapp.sunnyweather.logic.network.SunnyWeatherNetwork
import com.myapp.sunnyweather.util.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query:String)=liveData<Result<List<Place>>>(Dispatchers.IO) {
        LogUtil.d("wrong",query)
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlace(query)
            LogUtil.d("wrong","${placeResponse.status}|${placeResponse.places[0].name}")
            if (placeResponse.status=="ok"){
                LogUtil.d("wrong",placeResponse.status)
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }

        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
    fun refreshWeather(lng:String,lat:String)= liveData<Result<Weather>>(Dispatchers.IO){
        val result=try {
            coroutineScope {
                val deferredRealtime=async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily=async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                val realtimeResponse=deferredRealtime.await()
                val dailyResponse=deferredDaily.await()
                if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                    val weather=Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }
                else{
                    Result.failure(RuntimeException("realtimeWeather status is ${realtimeResponse.status}\n" +
                            "dailyWeather status is ${dailyResponse.status}"))
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
    fun savePlace(place: Place)= PlaceDao.savePlace(place)
    fun getSavedPlace()= PlaceDao.getSavedPlace()
    fun isSaved()= PlaceDao.isSaved()
}
