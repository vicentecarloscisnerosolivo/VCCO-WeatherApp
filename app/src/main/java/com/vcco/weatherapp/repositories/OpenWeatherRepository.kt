package com.vcco.weatherapp.repositories

import com.vcco.weatherapp.network.apiHelper.OpenWeatherApiHelper
import javax.inject.Inject

/**
 * Repository for OpenWeatherService, do network calls
 * @Inject: OpenWeatherApiHelper
 */

/*
* Continue the bridge, using network repository
* */
class OpenWeatherRepository @Inject constructor(private val helper: OpenWeatherApiHelper) {
    /**
     * getCurrentWeather from the requested location, could be GPS location or searched location by user
     * @param location String -> Could be following conditions: City or City and Country or City, State and Country
     * @param units String -> Could be Metric or Imperil by user selection
     *
     * Warning only can be called from not main thread function
     */

    fun getCurrentWeatherFromLocation(location: String, units: String) =
        helper.getWeatherCurrentLocation(location, units)
}