package com.vcco.weatherapp.model

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

data class Clouds(
    @SerializedName(Constants.CLOUD_COVERAGE_RESPONSE)
    val coverage: Int
)
