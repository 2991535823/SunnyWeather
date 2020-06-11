package com.myapp.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.myapp.sunnyweather.logic.Repository
import com.myapp.sunnyweather.logic.model.Place
import com.myapp.sunnyweather.util.log.LogUtil

class PlaceViewMode :ViewModel(){
    private val searchLiveData=MutableLiveData<String>()
    val placeList=ArrayList<Place>()
    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}