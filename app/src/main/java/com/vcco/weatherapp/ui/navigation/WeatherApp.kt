package com.vcco.weatherapp.ui.navigation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.vcco.weatherapp.R
import com.vcco.weatherapp.navigation.WeatherScreen
import com.vcco.weatherapp.ui.DetailWeatherScreen
import com.vcco.weatherapp.ui.toolbar.WeatherAppBar
import com.vcco.weatherapp.ui.home.HomeViewModel
import com.vcco.weatherapp.ui.screens.HomeScreen

/**
 * Create navigation for weather app
 */

//Using a separated navigation to have clean code

@Composable
fun WeatherApp(
    vm: HomeViewModel,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = WeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: WeatherScreen.Home.name
    )

    Scaffold(
        topBar = {
            WeatherAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        val isPermissionGranted = vm.isLocationPermissionGranted.observeAsState()
        val currentWeatherResponse = vm.currentWeatherResponse.observeAsState()
        val lastWeatherSearch = vm.lastSearchData.observeAsState()
        val showLastSearchButton = vm.canShowLastSearchButton.observeAsState()
        val isLoading = vm.isLoading.observeAsState()
        val errorState = vm.errorRetrievingData.observeAsState()
        var isFromLastSearchButton by remember { mutableStateOf(false) }

        NavHost(
            navController = navController,
            startDestination = WeatherScreen.Home.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = WeatherScreen.Home.name) {
                val context = LocalContext.current
                HomeScreen(
                    unitPreference = vm.getUnitPreference(),
                    canShowLocationButton = isPermissionGranted.value ?: false,
                    canShowLastSearchButton = showLastSearchButton.value ?: false,
                    onSearchWeatherCLicked = {
                        searchWeather(it, vm)
                        navController.navigate(WeatherScreen.Detail.name)
                    },
                    onSearchWeatherCurrentLocationClicked = {
                        getLastKnowLocation(context, vm)
                        navController.navigate(WeatherScreen.Detail.name)
                    },
                    onUnitSelectionChanged = { vm.updateUnitPreference(it) },
                    onLastWeatherSearchClicked = {
                        isFromLastSearchButton = true
                        navController.navigate(WeatherScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_16dp))
                )
            }
            composable(route = WeatherScreen.Detail.name) {
                DetailWeatherScreen(
                    isLoading = isLoading.value ?: false,
                    errorState = errorState.value ?: "",
                    currentWeather =
                    if (isFromLastSearchButton)
                        lastWeatherSearch.value
                    else
                        currentWeatherResponse.value,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * search weather from requested [location] could be zip code or place name
 */
private fun searchWeather(
    location: String,
    vm: HomeViewModel,
) {
    if (location.matches(".*\\d+.*".toRegex())) {
        //remove spaces, causing the search fail when is a valid zip code
        vm.getInfoFromZipCode(location.filter { !it.isWhitespace() })
    } else {
        vm.searchCityWeather(location)
    }
}

/**
 * Function used to get the last know location of the device, to retrieve location
 * and make network to search location
 */

private fun getLastKnowLocation(
    context: Context,
    vm: HomeViewModel
) {
    if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    vm.searchWeatherFromLocation(
                        it.latitude.toFloat(),
                        it.longitude.toFloat()
                    )
                }
            }
        } else {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
    }
}
