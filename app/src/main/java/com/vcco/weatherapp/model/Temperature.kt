package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class Temperature(
    @SerializedName(Constants.CURRENT_TEMPERATURE_RESPONSE)
    val temperature: Float,
    @SerializedName(Constants.CURRENT_TEMPERATURE_FEELS_LIKE_RESPONSE)
    val feelsLike: Float,
    @SerializedName(Constants.CURRENT_MIN_TEMPERATURE_RESPONSE)
    val minTemperature: Float,
    @SerializedName(Constants.CURRENT_MAX_TEMPERATURE_RESPONSE)
    val maxTemperature: Float,
    val humidity: Int
)
