package com.vcco.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vcco.weatherapp.repositories.OpenWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: OpenWeatherRepository) :
    ViewModel() {
    private var units: String = "metric"

    /**
     * Update the unit preference for Temperautre values
     * @param units: String
     */
    fun updateUnitPreference(units: String) {
        this.units = units
    }
}