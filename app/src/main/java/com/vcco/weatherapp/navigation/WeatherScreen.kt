package com.vcco.weatherapp.navigation

import androidx.annotation.StringRes
import com.vcco.weatherapp.R

/*
* Create this enum class to track navigation
* */

/**
 * Class for Screen in the weather app
 */
enum class WeatherScreen(@StringRes val screenTitle: Int) {
    Home(screenTitle = R.string.app_name),
    Detail(screenTitle = R.string.detail_screen)
}