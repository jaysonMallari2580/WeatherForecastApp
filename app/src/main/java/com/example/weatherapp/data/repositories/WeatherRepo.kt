package com.example.weatherapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.models.Forecast.ForecastResponseListDTO
import com.example.weatherapp.data.models.WeatherResponseDTO
import com.example.weatherapp.data.models.WeatherResponseListDTO
import com.example.weatherapp.data.remote.network.WeatherManager
import io.reactivex.Single

class WeatherRepo {

    fun getWeather(cityName:String="New York"): Single<WeatherResponseListDTO>{
        return WeatherManager().getWeather(cityName)
    }

    fun getForecast(/*lat:Double=33.44,lon:Double=-94.04*/lat:Double,lon:Double ): Single<ForecastResponseListDTO>{
        return WeatherManager().getForecast(lat,lon)
    }
}