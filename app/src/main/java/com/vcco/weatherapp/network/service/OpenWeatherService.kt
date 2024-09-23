package com.vcco.weatherapp.network.service

import com.vcco.weatherapp.model.geocoding.GeocodeResponse
import com.vcco.weatherapp.model.weather.CurrentWeatherResponse
import com.vcco.weatherapp.model.zip.ZipResponse
import com.vcco.weatherapp.network.utils.NetworkConstants as Constants
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
     * @return Observable<Response<CurrentWeatherResponse>>
     */

    @GET(Constants.WEATHER_URL)
    fun getCurrentWeather(
        @Query(Constants.QUERY, encoded = true) query: String,
        @Query(Constants.APY_KEY) apiKey: String,
        @Query(Constants.UNITS) units: String
    ): Observable<Response<CurrentWeatherResponse>>

    /**
     * Get Location info from Name
     * @param query: String
     * @param apiKey: String
     *
     * @return Observable<Response<List<GeocodeResponse>>>
     */
    @GET(Constants.GEOCODE_URL)
    fun getInfoWithLocationName(
        @Query(Constants.QUERY, encoded = true) query: String,
        @Query(Constants.APY_KEY) apiKey: String
    ): Observable<Response<List<GeocodeResponse>>>

    /**
     * Get Location Coordinates with Zip Code and country Code
     * @param zip: String -> Requested Zip with country code
     * @param apiKey: String
     *
     * @return  Observable<Response<ZipResponse>>
     */
    @GET(Constants.ZIP_URL)
    fun getInfoFromZipCode(
        @Query(Constants.ZIP, encoded = true) zip: String,
        @Query(Constants.APY_KEY) apiKey: String
    ): Observable<Response<ZipResponse>>

    /**
     * Get Info from Requested Coordinates
     * @param latitude: Float
     * @param longitude: Float
     * @param apiKey: String
     *
     * @return Observable<Response<List<GeocodeResponse>>>
     */
    @GET(Constants.REVERSE_GEOCODE_URL)
    fun getReverseLocation(
        @Query(Constants.LATITUDE) latitude: Float,
        @Query(Constants.LONGITUDE) longitude: Float,
        @Query(Constants.APY_KEY) apiKey: String
    ): Observable<Response<List<GeocodeResponse>>>
}