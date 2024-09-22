package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class WeatherResponse(
    @SerializedName(Constants.RESPONSE_DATA)
    val response: List<CurrentWeather>
)
