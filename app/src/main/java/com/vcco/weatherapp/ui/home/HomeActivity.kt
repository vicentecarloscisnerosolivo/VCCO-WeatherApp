package com.vcco.weatherapp.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.vcco.weatherapp.R
import com.vcco.weatherapp.ui.navigation.WeatherApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    /**
     * Implementation of homeViewModel, shared between screens
     *
     */

    //Normally create viewModel for screen, but for time only one and shared between screens
    private val vm: HomeViewModel by viewModels()

    //value used to show/hide locations weather
    private var canShowLocationButoon = false

    lateinit var snackbarHostState: SnackbarHostState

    /**
     * Request permissions callback, handle user interaction response
     * If the user declines not will have the feature of weather from location
     * but will show SnackBar to go to AppSettings and change permission
     */

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            vm.setLocationPermissionGranted(isGranted)
            if (!isGranted && ::snackbarHostState.isInitialized) {
                showPermissionsSnackBar()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            WeatherApp(
                vm = vm,
                snackbarHostState = snackbarHostState
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setObservers()
        checkLocationPermissions()
    }

    override fun onPause() {
        super.onPause()
        removeObservers()
    }

    /**
     * Observer live data from VM and handle it
     */
    /*
    * Handle in traditional activity/fragment
    * */
    private fun setObservers() {
        vm.errorRetrievingData.observe(this) {
            showErrorSnackBar(it)
        }
    }

    /**
     * Remove existing observers
     */
    /*
    * as handle on traditional activity/fragment
    * */
    private fun removeObservers() {
        vm.errorRetrievingData.removeObservers(this)
    }

    /**
     * Check if the application has the location permissions
     *
     * If the app not have permissions, request permissions
     */

    private fun checkLocationPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            canShowLocationButoon = true
            vm.setLocationPermissionGranted(canShowLocationButoon)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showPermissionsSnackBar()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /**
     * Show snack bar and if user click on action button launch permission settings of the app
     * to change configuration
     */
    private fun showPermissionsSnackBar() {
        lifecycleScope.launch {
            val result = snackbarHostState.showSnackbar(
                message = getString(R.string.snackbar_location_permission_should_request),
                actionLabel = getString(R.string.snackbar_action_settings),
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                val uri = Uri.fromParts("package", packageName, null)
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = uri
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
        }
    }

    /**
     * Show snack bar to notify user when there is an error retrieving information
     */

    private fun showErrorSnackBar(errorMessage: String) {
        lifecycleScope.launch {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
        }
    }
}
