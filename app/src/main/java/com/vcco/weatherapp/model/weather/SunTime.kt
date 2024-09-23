package com.vcco.weatherapp.model.weather

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

/**
 * Country code and expected sun rise and sun set in the requested location
 */
data class SunTime(
    val country: String,
    @SerializedName(Constants.SUN_TIME_SUNRISE_TIME_RESPONSE)
    val sunRiseTime: Long,
    @SerializedName(Constants.SUN_TIME_SUNSET_TIME_RESPONSE)
    val sunSetTimestamp: Long
)
