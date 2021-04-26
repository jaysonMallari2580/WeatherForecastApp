package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.models.Forecast.DailyDTO
import com.example.weatherapp.data.models.Forecast.ForecastResponseListDTO
import com.example.weatherapp.data.models.Forecast.HourlyDTO
import com.example.weatherapp.data.repositories.WeatherRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ForeCastViewModel: ViewModel() {

    private var _dailyForecastList = MutableLiveData<List<DailyDTO>>()
    val dailyForecastList get() = _dailyForecastList

    private var _forecastList = MutableLiveData<List<HourlyDTO>>()
    val forecastList get() = _forecastList

    private var _lat = MutableLiveData<Double>()
    val lat get() = _lat

    private var _lon = MutableLiveData<String>()
    val lon get() = _lon

    private val weatherRepo : WeatherRepo by lazy{
        WeatherRepo()
    }

    private val disposal = CompositeDisposable()
    init{

    }

    private fun getForecast(lat:Double,lon: Double) =
        disposal.add(
            weatherRepo.getForecast(lat,lon).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetForecastSuccess, this::onGetForecastError)
        )

    private fun onGetForecastSuccess(forecastResponse: ForecastResponseListDTO) {
        _forecastList.value = forecastResponse.hourlyList

        _dailyForecastList.value = forecastResponse.dailyList

    }

    fun getForecastByCityname(lat:Double,lon: Double){
        getForecast(lat,lon)
    }


    private fun onGetForecastError(e:Throwable){
        e.message.let { Log.d(TAG,it.toString()) }
    }

    companion object{
        val TAG = ForeCastViewModel::class.java.name
    }
}