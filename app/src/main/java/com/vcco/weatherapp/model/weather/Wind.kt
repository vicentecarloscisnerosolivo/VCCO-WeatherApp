package com.vcco.weatherapp.model.weather

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

/**
 * Current Wind conditions in the location
 */
data class Wind(
    val speed: Float,
    @SerializedName(Constants.WIND_DIRECTION_RESPONSE)
    val direction: Int
)
