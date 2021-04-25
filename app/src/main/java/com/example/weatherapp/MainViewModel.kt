package com.example.weatherapp
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.models.CityDTO
import com.example.weatherapp.data.models.WeatherResponseDTO
import com.example.weatherapp.data.models.WeatherResponseListDTO
import com.example.weatherapp.data.repositories.WeatherRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

    //lat &lon
    private var _lat= MutableLiveData<Double>()
    val lat get() = _lat

    private var _lon= MutableLiveData<Double>()
    val lon get() = _lon

    private var _weatherList = MutableLiveData<List<WeatherResponseDTO>>()
    val weatherList get() = _weatherList

    private var _weatherResponse = MutableLiveData<WeatherResponseDTO>()
    val weatherResponse get() = _weatherResponse

    private var _cityName = MutableLiveData<String>()
    val cityName get() = _cityName

    private val disposable = CompositeDisposable()
    private val weatherRepo: WeatherRepo by lazy {
        WeatherRepo()
    }

    init {
    }

    private var _test = MutableLiveData<String>()
    val test get() = _test

    private var _mainInfoMinTemp = MutableLiveData<String>()
    val mainInfoMinTemp = _mainInfoMinTemp

    private var _mainInfoMaxTemp = MutableLiveData<String>()
    val mainInfoMaxTemp = _mainInfoMaxTemp

    private var _mainInfo = MutableLiveData<String>()
    val mainInfo get() = _mainInfo

    private var _cityNameInfo = MutableLiveData<String>()
    val cityNameInfo get() = _cityNameInfo

    private var _weatherHint = MutableLiveData<String>()
    val weatherHint get() = _weatherHint

    private var _icon = MutableLiveData<String>()
    val icon get() = _icon

    private fun getWeather(cityName: String) =
        disposable.add(
            weatherRepo.getWeather(cityName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetWeatherSuccess, this::onGetWeatherError)
        )

    private fun onGetWeatherSuccess(weatherResponse: WeatherResponseListDTO) {

        _lat.value = weatherResponse.city?.coord?.lat
        _lon.value = weatherResponse.city?.coord?.lon

        _weatherList.value = weatherResponse.list

        _test.value = weatherResponse.list[0].mainDTO.temp

        //temp
        _mainInfo.value = calculateFahrenheit(weatherResponse.list[0].mainDTO.temp!!.toDouble()).toInt().toString()+"\u2109"

        _mainInfoMaxTemp.value = calculateFahrenheit(weatherResponse.list[0].mainDTO.temp_max!!.toDouble()).toInt().toString()

        _mainInfoMinTemp.value = calculateFahrenheit(weatherResponse.list[0].mainDTO.temp_min!!.toDouble()).toInt().toString()
        //cityName
        _cityNameInfo.value = weatherResponse.city?.name

        //weather Hint
        _weatherHint.value = weatherResponse.list[0].weatherListDTO[0].description.toString()

        //weather Icon
        _icon.value = weatherResponse.list[0].weatherListDTO[0].icon.toString()
    }


    private fun onGetWeatherError(e: Throwable) {
        e.message.let { Log.d(TAG, it.toString())}
    }


    fun passMeTheCityName(cityName:String){
        getWeather(cityName)
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }

    private fun calculateFahrenheit(degrees: Double): Double {
        val degreesInFahrenheit = (degrees * 1.8) + 32
        return degreesInFahrenheit
    }



}


