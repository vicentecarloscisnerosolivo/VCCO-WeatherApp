package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class Conditions(
    val id: Int,
    @SerializedName(Constants.CONDITIONS_MAIN_CONDITION_RESPONSE)
    val condition: String,
    val description: String,
    val icon: String
)
