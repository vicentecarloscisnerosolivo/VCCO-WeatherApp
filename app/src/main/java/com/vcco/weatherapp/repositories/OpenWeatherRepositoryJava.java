package com.vcco.weatherapp.repositories;


import com.vcco.weatherapp.model.geocoding.GeocodeResponse;
import com.vcco.weatherapp.model.weather.CurrentWeatherResponse;
import com.vcco.weatherapp.model.zip.ZipResponse;
import com.vcco.weatherapp.network.apiHelper.OpenWeatherApiHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

/**
 * Repository for OpenWeatherService, do network calls on Java
 *
 * @Inject: OpenWeatherApiHelper
 */

/*
 * Continue the bridge, using network repository, using this clas as Java to show to handle java
 * for more examples link to my github page where I have code challenges made on java
 * https://github.com/vicentecarloscisnerosolivo?tab=repositories
 */
public class OpenWeatherRepositoryJava {

    private final OpenWeatherApiHelper helper;

    @Inject
    public OpenWeatherRepositoryJava(OpenWeatherApiHelper helper) {
        this.helper = helper;
    }


    /**
     * getCurrentWeather from the requested location, could be GPS location or searched location
     * by user
     *
     * @param location String -> Could be following conditions: City or City and Country or City,
     *                 State and Country
     * @param units    String -> Could be Metric or Imperil by user selection
     */
    public Observable<Response<CurrentWeatherResponse>> getCurrentWeatherFromLocation(String location, String units) {
        return helper.getWeatherCurrentLocation(location, units);
    }

    /**
     * get the location information like coordinates local names from the name of the City
     *
     * @param location String -> Could be following conditions: City or City and Country or City,
     *                 State and Country
     */
    public Observable<Response<List<GeocodeResponse>>> getLocationInfoFromName(String location) {
        return helper.getLocationInfoFromName(location);
    }

    /**
     * get the location info for the giving ZipCode
     *
     * @param zipCode String -> , must use ZipCode,Country Code ie. 90210, US
     */

    public Observable<Response<ZipResponse>> getInfoFromZipCode(String zipCode) {
        return helper.getInfoFromZipCode(zipCode);
    }


    /**
     * get location Info from the requested Coordinates
     *
     * @param latitude  Float -> latitude from the location
     * @param longitude Float -> longitude from the location
     *                  <p>
     *                  Warning only can be called from not async threat
     */

    public Observable<Response<List<GeocodeResponse>>> getLocationInfoFromCoordinates(Float latitude, Float longitude) {
        return helper.getReverseLocation(latitude, longitude);
    }
}
