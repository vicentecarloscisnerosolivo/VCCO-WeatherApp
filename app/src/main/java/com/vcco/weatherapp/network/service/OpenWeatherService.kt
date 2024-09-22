package com.vcco.weatherapp.network.service

import com.vcco.weatherapp.model.CurrentWeather
import com.vcco.weatherapp.model.WeatherResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface used for Network API
 */
/*
* Using bridge Structural design to do simplify the web service call trough OpenWeatherApiHelper
*/
interface OpenWeatherService {

    /**
     * Request Current Weather Information from Requested Location
     * @param query: String
     * @param apiKey: String
     * @param units: String
     *
     * @return Weather response
     */

    @GET("weather")
    fun getCurrentWeather(
        @Query("q", encoded = true) query: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Observable<Response<CurrentWeather>>

}