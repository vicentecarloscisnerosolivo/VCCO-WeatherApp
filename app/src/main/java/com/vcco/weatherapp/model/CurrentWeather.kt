package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class CurrentWeather(
    @SerializedName(Constants.CURRENT_WEATHER_COORDINATE_RESPONSE)
    val coordinates: Coordinates,
    @SerializedName(Constants.CURRENT_WEATHER_CONDITIONS_RESPONSE)
    val conditions: Conditions,
    @SerializedName(Constants.CURRENT_WEATHER_TEMPERATURE_RESPONSE)
    val temperature: Temperature,
    @SerializedName(Constants.CURRENT_WEATHER_VISIBILITY_RESPONSE)
    val visibility: Int,
    @SerializedName(Constants.CURRENT_WEATHER_WIND_RESPONSE)
    val wind: Wind,
    @SerializedName(Constants.CURRENT_WEATHER_CLOUDS_RESPONSE)
    val clouds: Clouds,
    @SerializedName(Constants.CURRENT_WEATHER_RAIN_RESPONSE)
    val rain: Rain?,
    @SerializedName(Constants.CURRENT_WEATHER_SNOW_RESPONSE)
    val snow: Snow?,
    @SerializedName(Constants.CURRENT_WEATHER_DATA_CALCULATION_RESPONSE)
    val dataCalculation: Timestamp,
    @SerializedName(Constants.CURRENT_WEATHER_SUN_TIME_RESPONSE)
    val sunTime: SunTime,
    @SerializedName(Constants.CURRENT_WEATHER_TIME_ZONE_RESPONSE)
    val timeZone: Long,
    @SerializedName(Constants.CURRENT_WEATHER_ID_RESPONSE)
    val id: Int,
    @SerializedName(Constants.CURRENT_WEATHER_NAME_RESPONSE)
    val name: String
)
