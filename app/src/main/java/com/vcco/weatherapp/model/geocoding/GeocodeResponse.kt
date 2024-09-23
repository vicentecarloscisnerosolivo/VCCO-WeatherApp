package com.vcco.weatherapp.model.geocoding

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

/**
 * Geocode info form  specific location retrieved from https://openweathermap.org/
 */
data class GeocodeResponse(
    @SerializedName(Constants.NAME_RESPONSE)
    val name: String,
    @SerializedName(Constants.GEOCODE_LOCAL_NAMES_RESPONSE)
    val localNames: Map<String, String>,
    @SerializedName(Constants.LATITUDE_RESPONSE)
    val latitude: Float,
    @SerializedName(Constants.LONGITUDE_RESPONSE)
    val longitude: Float,
    @SerializedName(Constants.COUNTRY_RESPONSE)
    val country: String,
    @SerializedName(Constants.GEOCODE_STATE_RESPONSE)
    val state: String?
)
