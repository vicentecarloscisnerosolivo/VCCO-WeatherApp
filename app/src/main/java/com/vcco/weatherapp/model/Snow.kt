package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class Snow(
    @SerializedName(Constants.SNOW_AMOUNT_RESPONSE)
    val amount: Float
)
