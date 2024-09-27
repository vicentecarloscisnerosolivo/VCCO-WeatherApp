package com.vcco.weatherapp.ui.home

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vcco.weatherapp.BuildConfig
import com.vcco.weatherapp.R
import com.vcco.weatherapp.model.weather.CurrentWeatherResponse
import com.vcco.weatherapp.repositories.OpenWeatherRepository
import com.vcco.weatherapp.repositories.OpenWeatherRepositoryJava
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: OpenWeatherRepository,
    private val javaRepositoryJava: OpenWeatherRepositoryJava
) : ViewModel() {
    /**
     * Value used for Log identification
     */
    private val TAG = javaClass.name

    /**
     * Value from 404 response
     */
    private val notFoundMessage = "not found"

    /**
     * value used as control for default metric value
     */
    private var units: String = "metric"

    /**
     * Key used on datastore for units preference
     */
    private val unitKey = "unitValue"

    /**
     * Key used on datastore for last search
     */
    private val lastSearchKey = "lastWeatherSearch"

    /**
     * Handle if user grant location permissions
     */
    private val _isLocationPermissionGranted = MutableLiveData(false)
    val isLocationPermissionGranted: LiveData<Boolean> = _isLocationPermissionGranted

    /**
     * error handler, to notify user
     */

    private val _errorRetrievingData = MutableLiveData<String>()
    val errorRetrievingData: LiveData<String> = _errorRetrievingData

    /**
     * user to enable/disable layout and show loading bar
     */

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * indicate if can show data store or not
     */

    private val _canShowLastSearchButton = MutableLiveData(false)
    val canShowLastSearchButton: LiveData<Boolean> = _canShowLastSearchButton

    /**
     *
     */
    private val _lastSearchData = MutableLiveData<CurrentWeatherResponse>()
    val lastSearchData: LiveData<CurrentWeatherResponse> = _lastSearchData

    /**
     * live data for weatherResponse
     */

    private val _currentWeather = MutableLiveData<CurrentWeatherResponse?>()
    val currentWeatherResponse: LiveData<CurrentWeatherResponse?> = _currentWeather

    private val Context.dataStore by preferencesDataStore(name = BuildConfig.PREFERENCE_FILE)

    /*
    * init data and get units saved data preference and last search
    * */
    init {
        runBlocking {
            val result = context.dataStore.data.map { preference ->
                preference[stringPreferencesKey(unitKey)] ?: units
            }
            units = result.first()
            val lastSearch = context.dataStore.data.map { preference ->
                preference[stringPreferencesKey(lastSearchKey)]
            }
            lastSearch.first()?.let {
                val value: CurrentWeatherResponse =
                    Gson().fromJson(it, CurrentWeatherResponse::class.java)
                _lastSearchData.postValue(value)
                _canShowLastSearchButton.postValue(true)
            }

        }
    }

    /**
     * Get the current unit preference to show in units
     */
    /*
    * according to documentation the function String.capitalize is deprecated, recommending
    * use this way
    * */
    fun getUnitPreference() = units.replaceFirstChar { it.uppercase() }


    /**
     * Update the unit preference for Temperautre values
     * @param units: String
     */
    fun updateUnitPreference(units: String) {
        this.units = units
        viewModelScope.launch {
            context.dataStore.edit { preference ->
                preference[stringPreferencesKey(unitKey)] = units.lowercase()
            }
        }
    }

    /**
     * Update the LiveData of the state, only used to show how handle LiveData on compose
     */
    fun setLocationPermissionGranted(isGranted: Boolean) {
        _isLocationPermissionGranted.postValue(isGranted)
    }

    /**
     * Search weather from [location], feel free to add city name, state if you place and Country
     *
     */

    fun searchCityWeather(location: String, isFromZipCode: Boolean = false) {
        _currentWeather.postValue(null)
        viewModelScope.launch {
            repository.getCurrentWeatherFromLocation(location, units)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)
                        _currentWeather.postValue(response.body())
                        response.body()?.let { saveLastSearch(it) }
                    } else if ((response.raw().message == notFoundMessage || response.raw().code == 404) && !isFromZipCode) {
                        Log.i(TAG, "Retrieving data from zip code")
                        getInfoFromZipCode(location)
                    }
                }, {
                    it.printStackTrace()
                    _isLoading.postValue(false)
                    _errorRetrievingData.postValue(context.getString(R.string.error_server_error))
                })
        }
    }

    /**
     * Search with [zipCode] to get name of the city, state and country
     */

    fun getInfoFromZipCode(zipCode: String) {
        viewModelScope.launch {
            repository.getInfoFromZipCode(zipCode)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            val query = context.getString(
                                R.string.format_string_for_city_query,
                                data.name,
                                "",
                                data.country
                            )
                            searchCityWeather(query, isFromZipCode = true)
                        } else {
                            _isLoading.postValue(false)
                            _errorRetrievingData.postValue(context.getString(R.string.error_not_found_location))
                        }
                    } else if (response.raw().code == 404 || response.raw().message == notFoundMessage) {
                        _isLoading.postValue(false)
                        _errorRetrievingData.postValue(context.getString(R.string.error_not_found_location))
                    }
                }, {
                    it.printStackTrace()
                    _isLoading.postValue(false)
                    _errorRetrievingData.postValue(context.getString(R.string.error_server_error))
                })
        }
    }

    /**
     * Search current weatherInfo from [latitude] and [longitude], this only
     * accessible when user request it manually duo time
     */
    fun searchWeatherFromLocation(latitude: Float, longitude: Float) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            javaRepositoryJava.getLocationInfoFromCoordinates(latitude, longitude)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        val resultCity = response.body()?.firstOrNull()
                        if (resultCity != null) {
                            /*
                             * when we have a success search of city name, create a query
                             * to get current weather from location
                             */
                            val location = context.getString(
                                R.string.format_string_for_city_query,
                                resultCity.name,
                                resultCity.state,
                                resultCity.country
                            )
                            searchCityWeather(location)
                        } else {
                            _isLoading.postValue(false)
                            _errorRetrievingData.postValue(context.getString(R.string.error_server_error))
                        }
                    }
                }, {
                    it.printStackTrace()
                    _isLoading.postValue(false)
                    _errorRetrievingData.postValue(context.getString(R.string.error_server_error))
                })
        }
    }

    private fun saveLastSearch(lastWeatherSearch: CurrentWeatherResponse) {
        viewModelScope.launch {
            _canShowLastSearchButton.postValue(true)
            _lastSearchData.postValue(lastWeatherSearch)
            context.dataStore.edit { preference ->
                val json = Gson().toJson(lastWeatherSearch)
                preference[stringPreferencesKey(lastSearchKey)] = json
            }
        }
    }
}
