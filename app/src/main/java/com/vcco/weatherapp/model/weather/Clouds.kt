package com.vcco.weatherapp.model.weather

import com.google.gson.annotations.SerializedName
import com.vcco.weatherapp.model.utils.ModelConstants as Constants

/**
 * Shows Cloud coverage in the requested location
 */
data class Clouds(
    @SerializedName(Constants.CLOUD_COVERAGE_RESPONSE)
    val coverage: Int
)
