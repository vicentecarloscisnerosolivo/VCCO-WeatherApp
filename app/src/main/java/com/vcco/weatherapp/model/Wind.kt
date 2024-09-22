package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class Wind(
    val speed: Float,
    @SerializedName(Constants.WIND_DIRECTION_RESPONSE)
    val direction: Float
)
