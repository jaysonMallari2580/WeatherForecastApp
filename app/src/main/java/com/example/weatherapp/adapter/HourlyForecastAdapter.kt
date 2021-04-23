package com.example.weatherapp.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.models.WeatherResponseDTO
import com.example.weatherapp.data.models.WeatherResponseListDTO
import com.example.weatherapp.databinding.HourlyWeatherItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HourlyForecastAdapter(): RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    val mHourlyList: ArrayList<WeatherResponseDTO> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        return HourlyForecastViewHolder(HourlyWeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val hourlyForecast = mHourlyList[position]

        holder.loadData(hourlyForecast)

        var iconCode = hourlyForecast.weatherListDTO[position].icon
        var iconUrl = "https://openweathermap.org/img/w/$iconCode.png";
        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.binding.hourIconIv)

    }

    override fun getItemCount(): Int {
        return mHourlyList.size
    }

    class HourlyForecastViewHolder(val binding:HourlyWeatherItemBinding):RecyclerView.ViewHolder(binding.root) {

            fun loadData(hourlyForecast: WeatherResponseDTO) {
            // label
            val date = Date(hourlyForecast.time * 1000)
            val simpleDateFormat = SimpleDateFormat("hh")
                simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                println(simpleDateFormat.format(date))
            val timeLable = simpleDateFormat.format(date)
                binding.hourNameTv.text =timeLable

             //temp
                val temp = calculateFahrenheit(hourlyForecast.mainDTO.temp!!.toDouble()).toInt().toString()
                binding.hourlyTemp.text = temp
            }

        private fun calculateFahrenheit(degrees: Double): Double {
            val degreesInFahrenheit = (degrees * 1.8) + 32
            return degreesInFahrenheit
        }

    }
}