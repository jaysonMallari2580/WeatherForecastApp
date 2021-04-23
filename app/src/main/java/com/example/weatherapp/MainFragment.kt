package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapter.HourlyForecastAdapter
import com.example.weatherapp.databinding.MainFragmentBinding
import java.time.LocalDate

class MainFragment : Fragment() {

    lateinit var binding: MainFragmentBinding
    lateinit var adapterH: HourlyForecastAdapter
    lateinit var recyclerView: RecyclerView

    private val hourlyForecastAdapter: HourlyForecastAdapter by lazy {
        HourlyForecastAdapter()
    }

    companion object {
        fun newInstance() = MainFragment()

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var weatherResponseList = viewModel.weatherList

        binding.hourlyWeatherRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyForecastAdapter

            println("This is the list $weatherResponseList")
        }


        //temp
        viewModel.mainInfo.observe(viewLifecycleOwner, Observer {
            binding.tempTextview.text = it.toString()
        })

        viewModel.mainInfoMaxTemp.observe(viewLifecycleOwner, Observer {
            binding.currentWeatherHighTv.text = it.toString()
        })

        viewModel.mainInfoMaxTemp.observe(viewLifecycleOwner, Observer {
            binding.currentWeatherLowTv.text = it.toString()
        })

        //cityname
        viewModel.cityNameInfo.observe(viewLifecycleOwner, Observer {
            binding.loactionTextview.text = it.toString()
        })

        //weather hint
        viewModel.weatherHint.observe(viewLifecycleOwner, Observer {
            binding.weatherTextview.text = it.toString()
        })

        /*viewModel.icon.observe(viewLifecycleOwner, Observer {
            var iconCode = it.toString()
            var iconUrl = "https://openweathermap.org/img/w/$iconCode.png";
            Glide.with(requireView())
                .load(iconUrl)
                .into(binding.weatherIcon)
        })*/


        //Search
        binding.searchIcon.setOnClickListener(View.OnClickListener {

            var cityName = binding.searchEdittext.editText?.text.toString()
            /*binding.test.text = cityName?.text*/
            viewModel.passMeTheCityName(cityName)

            val toast =
                Toast.makeText(context, "You have entered $cityName. . .", Toast.LENGTH_LONG)
            toast.show()

            it.hideKeyboard()
            binding.searchEdittext.editText?.setText("")

            //Day
            var day: String = LocalDate.now().dayOfWeek.name
            binding.todaysNameTextview.setText(day)

        })
    }


}