package com.vcco.weatherapp.network.apiHelper

import com.vcco.weatherapp.model.CurrentWeather
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

/**
 * Helper used to call OpenWeatherService
 */
/*
* Continue with the bridge, here will create the interface to call OpenWeatherService using
* OpenWeatherApiHelper as Proxy as to control access
*/
interface OpenWeatherApiHelper {
    /**
     * Request Current Weather Information from Requested Location
     * @param query: String
     * @param units: String
     *
     * @return Weather response
     */
    fun getWeatherCurrentLocation(
        query: String,
        units: String
    ): Observable<Response<CurrentWeather>>
}