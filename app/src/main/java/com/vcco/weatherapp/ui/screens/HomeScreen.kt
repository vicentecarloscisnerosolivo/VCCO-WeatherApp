package com.vcco.weatherapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.vcco.weatherapp.R

/**
 * Create the home screen view receiving the [onSearchWeatherCurrentLocationClicked] for use
 * the device location, [onSearchWeatherCLicked] when
 * search weather for city and [onLastWeatherSearchClicked] to retrieve the last search and update
 * user preference [onUnitSelectionChanged]
 */
@Composable
fun HomeScreen(
    unitPreference: String,
    canShowLocationButton: Boolean,
    canShowLastSearchButton: Boolean,
    onSearchWeatherCLicked: (String) -> Unit,
    onSearchWeatherCurrentLocationClicked: () -> Unit,
    onLastWeatherSearchClicked: () -> Unit,
    onUnitSelectionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp))
        ) {
            var locationSearch by remember { mutableStateOf("") }

            OutlinedTextField(
                value = locationSearch,
                onValueChange = { locationSearch = it },
                label = { Text(stringResource(R.string.label_search_city)) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(dimensionResource(R.dimen.padding_8dp))
                    .fillMaxWidth()
            )
            val context = LocalContext.current
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_8dp)),
                onClick = {
                    if (locationSearch.isNotBlank()) {
                        onSearchWeatherCLicked(locationSearch)
                    } else {
                        Toast.makeText(context, R.string.label_search_city, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(stringResource(R.string.button_search))
            }

            if (canShowLocationButton) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_8dp)),
                    onClick = {
                        onSearchWeatherCurrentLocationClicked()
                    }
                ) {
                    Text(stringResource(R.string.button_search_current_location))
                }
            }

            if (canShowLastSearchButton) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_8dp)),
                    onClick = {
                        onLastWeatherSearchClicked()
                    }
                ) {
                    Text(stringResource(R.string.button_show_last_search))
                }
            }
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.label_select_unit),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_8dp)),
                horizontalArrangement = Arrangement.Center
            ) {
                var selectedUnit by rememberSaveable { mutableStateOf(unitPreference) }
                val imperialOption = stringResource(R.string.radio_button_imperial_option)
                val metricOption = stringResource(R.string.radio_button_metric_option)
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedUnit == imperialOption,
                        onClick = {
                            selectedUnit = imperialOption
                            onUnitSelectionChanged(selectedUnit)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedUnit == imperialOption,
                        onClick = {
                            selectedUnit = imperialOption
                            onUnitSelectionChanged(selectedUnit)
                        }
                    )
                    Text(imperialOption)
                }
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedUnit == metricOption,
                        onClick = {
                            selectedUnit = metricOption
                            onUnitSelectionChanged(metricOption)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedUnit == metricOption,
                        onClick = {
                            selectedUnit = metricOption
                            onUnitSelectionChanged(metricOption)
                        }
                    )
                    Text(metricOption)
                }
            }
        }
    }
}


/**
 * Create a preview of the Home Screen
 */

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        unitPreference = "metric".replaceFirstChar { it.uppercase() },
        canShowLocationButton = true,
        canShowLastSearchButton = true,
        onSearchWeatherCLicked = {},
        onSearchWeatherCurrentLocationClicked = {},
        onUnitSelectionChanged = {},
        onLastWeatherSearchClicked = {},
        modifier = Modifier.fillMaxSize()
    )
}
