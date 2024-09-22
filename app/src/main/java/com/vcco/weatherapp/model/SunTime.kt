package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants
import java.sql.Timestamp

data class SunTime(
    val country: String,
    @SerializedName(Constants.SUN_TIME_SUNRISE_TIME_RESPONSE)
    val sunRiseTime: Timestamp,
    @SerializedName(Constants.SUN_TIME_SUNSET_TIME_RESPONSE)
    val sunSetTimestamp: Timestamp
)
