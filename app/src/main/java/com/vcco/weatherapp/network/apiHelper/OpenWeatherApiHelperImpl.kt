package com.vcco.weatherapp.network.apiHelper

import com.vcco.weatherapp.BuildConfig
import com.vcco.weatherapp.model.CurrentWeather
import com.vcco.weatherapp.network.service.OpenWeatherService
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * Helper used to call OpenWeatherService
 */
/*
* Implement interface from OpenWeatherApiHelper
* */
class OpenWeatherApiHelperImpl @Inject constructor(private val service: OpenWeatherService) :
    OpenWeatherApiHelper {
    //retrieve from the Build Config the Api key value, using lazy to init the value in the moment that wil need it, and no when class is created
    private val apiKey by lazy { BuildConfig.API_KEY }

    //TAG for Log
    private val TAG = OpenWeatherApiHelperImpl::class.simpleName

    override fun getWeatherCurrentLocation(query: String, units: String) =
        service.getCurrentWeather(query, apiKey, units)

}