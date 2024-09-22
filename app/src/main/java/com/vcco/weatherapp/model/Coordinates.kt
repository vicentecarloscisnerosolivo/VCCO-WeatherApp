package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Consntants

data class Coordinates(
    @SerializedName(Consntants.LONGITUDE_RESPONSE)
    val longitude: Float,
    @SerializedName(Consntants.LATITUDE_RESPONSE)
    val latitude: Float
)
