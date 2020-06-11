package com.myapp.sunnyweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.sunnyweather.MainActivity
import com.myapp.sunnyweather.R
import com.myapp.sunnyweather.ui.weather.WeatherActivity
import com.myapp.sunnyweather.util.Toast.showToast
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewMode::class.java) }
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity&&viewModel.isSaved()){
            val place=viewModel.getSavedPlace()
            val intent= Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager=LinearLayoutManager(activity)
        recyclerview.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewModel.placeList)
        recyclerview.adapter=adapter
        searchPlaceEdit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val content=p0.toString()
                if (content.isNotEmpty()){
                    viewModel.searchPlaces(content)
                }else{
                    recyclerview.visibility=View.GONE
                    bgImageView.visibility=View.VISIBLE
                    viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
                    "想看天气就请输入地点哦".showToast()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
        viewModel.placeLiveData.observe(this, Observer { result->
            val place=result.getOrNull()
            if (place!=null){
                recyclerview.visibility=View.VISIBLE
                bgImageView.visibility=View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
                adapter.notifyDataSetChanged()
            }else{
                "未能查询到任何地点".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}