package com.vcco.weatherapp.ui.screens

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.vcco.weatherapp.R
import com.vcco.weatherapp.model.weather.Clouds
import com.vcco.weatherapp.model.weather.Conditions
import com.vcco.weatherapp.model.weather.Coordinates
import com.vcco.weatherapp.model.weather.CurrentWeatherResponse
import com.vcco.weatherapp.model.weather.Rain
import com.vcco.weatherapp.model.weather.Snow
import com.vcco.weatherapp.model.weather.SunTime
import com.vcco.weatherapp.model.weather.Temperature
import com.vcco.weatherapp.model.weather.Wind
import com.vcco.weatherapp.ui.theme.LabelText
import com.vcco.weatherapp.ui.theme.LabelTextLight
import java.util.Calendar
import java.util.Locale

/**
 * Shows detail information of weather
 */
@Composable
fun DetailWeatherScreen(
    isLoading: Boolean,
    errorState: String,
    currentWeather: CurrentWeatherResponse?,
    modifier: Modifier = Modifier
) {
    if (currentWeather == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = dimensionResource(R.dimen.padding_16dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                Text(
                    text = errorState,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.text_size_32_sp).value.sp,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_8dp))
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    } else {
        val currentConditions = currentWeather.conditions.first()
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp))
            ) {

                Text(
                    text = currentWeather.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.text_size_32_sp).value.sp,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_8dp))
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(
                            convertURLToDrawableId(
                                currentConditions.icon
                            )
                        ),
                        contentDescription = currentConditions.description,
                        alignment = Alignment.CenterEnd,
                        modifier = modifier.weight(1f)
                    )
                    Text(
                        text = currentConditions.condition,
                        modifier = modifier
                            .weight(1f)
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
                LabelText(
                    text = stringResource(R.string.label_current_temperature),
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(
                            R.string.label_current_temperature_format,
                            currentWeather.temperature.temperature.toInt()
                        ),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_size_64_sp).value.sp,
                        modifier = modifier
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
                LabelTextLight(
                    text = stringResource(
                        R.string.label_temperature_real_feel,
                        currentWeather.temperature.feelsLike.toInt()
                    ),
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(
                            R.string.label_temperature_min,
                            currentWeather.temperature.minTemperature.toInt()
                        ),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Light,
                        modifier = modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(end = dimensionResource(R.dimen.padding_8dp))
                            .weight(1f)
                    )
                    Text(
                        text = stringResource(
                            R.string.label_temperature_max,
                            currentWeather.temperature.maxTemperature.toInt()
                        ),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light,
                        modifier = modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = dimensionResource(R.dimen.padding_8dp))
                            .weight(1f)
                    )
                }
                LabelTextLight(
                    text = stringResource(
                        R.string.label_current_humidity,
                        currentWeather.temperature.humidity
                    ),
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                LabelTextLight(
                    text = stringResource(
                        R.string.label_cloud_coverage,
                        currentWeather.clouds.coverage
                    ),
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                val precipitation = if (currentWeather.rain != null) {
                    stringResource(R.string.label_current_rain_expected, currentWeather.rain.amount)
                } else if (currentWeather.snow != null) {
                    stringResource(R.string.label_current_rain_expected, currentWeather.snow.amount)
                } else {
                    stringResource(R.string.label_current_no_precipitations)
                }
                LabelTextLight(
                    text = precipitation,
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_16dp)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16dp)),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    LabelText(
                        text = stringResource(
                            R.string.label_current_wind
                        ),
                        modifier = modifier
                    )
                    LabelTextLight(
                        text = stringResource(
                            R.string.label_current_wind_speed,
                            currentWeather.wind.speed
                        ),
                        modifier = modifier
                    )
                    val direction = currentWeather.wind.direction
                    val windDirection = when (direction) {
                        in 23..66 -> R.string.label_current_wind_north_east
                        in 67..111 -> R.string.label_current_wind_east
                        in 112..156 -> R.string.label_current_wind_south_east
                        in 157..202 -> R.string.label_current_wind_south
                        in 203..247 -> R.string.label_current_wind_south_west
                        in 248..291 -> R.string.label_current_wind_west
                        in 292..337 -> R.string.label_current_wind_north_west
                        else -> R.string.label_current_wind_north
                    }
                    LabelTextLight(
                        text = stringResource(windDirection),
                        modifier = modifier
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    val cal = Calendar.getInstance(Locale.ENGLISH)
                    cal.timeInMillis = currentWeather.sunTime.sunRiseTimestamp * 1000L
                    var sunTime = DateFormat.format("hh:mm a", cal).toString()
                    LabelTextLight(
                        text = stringResource(R.string.label_today_sunrise, sunTime),
                        modifier = modifier
                    )
                    cal.timeInMillis = currentWeather.sunTime.sunSetTimestamp * 1000L
                    sunTime = DateFormat.format("hh:mm a", cal).toString()
                    LabelTextLight(
                        text = stringResource(
                            R.string.label_today_sunset,
                            sunTime
                        ),
                        modifier = modifier
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_16dp)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16dp)),
                verticalAlignment = Alignment.Bottom
            ) {
                val cal = Calendar.getInstance(Locale.ENGLISH)
                cal.timeInMillis = currentWeather.dataCalculation * 1000L
                val searchTime = DateFormat.format("MM/dd/yyyy hh:mm a", cal).toString()
                LabelTextLight(
                    text = stringResource(R.string.label_data_search_date, searchTime),
                    modifier = modifier
                )
            }
        }
    }
}

/**
 * get the local asset from the local asset
 */
private fun convertURLToDrawableId(url: String): Int {
    return when (url) {
        "01d" -> R.drawable.ic_1d
        "01n" -> R.drawable.ic_1n
        "02d" -> R.drawable.ic_2d
        "02n" -> R.drawable.ic_2n
        "03d" -> R.drawable.ic_3d
        "03n" -> R.drawable.ic_3n
        "04d" -> R.drawable.ic_4d
        "04n" -> R.drawable.ic_4n
        "09d" -> R.drawable.ic_9d
        "09n" -> R.drawable.ic_9n
        "10d" -> R.drawable.ic_10d
        "10n" -> R.drawable.ic_10n
        "11d" -> R.drawable.ic_11d
        "11n" -> R.drawable.ic_11n
        "13d" -> R.drawable.ic_13d
        "13n" -> R.drawable.ic_13n
        else -> R.drawable.ic_50n
    }
}

/**
 * Create a preview Detail WeatherScreen
 */
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewDetailWeatherScreen() {
    DetailWeatherScreen(
        isLoading = false,
        errorState = "",
        currentWeather =
        CurrentWeatherResponse(
            coordinates = Coordinates(
                longitude = -96.9489f,
                latitude = 32.814f
            ),
            conditions = listOf(
                Conditions(
                    id = 1,
                    condition = "clear",
                    description = "clear",
                    icon = "01d"
                )
            ),
            temperature = Temperature(
                temperature = 30.0f,
                feelsLike = 31.1f,
                minTemperature = 20.0f,
                maxTemperature = 32.0f,
                humidity = 60
            ),
            visibility = 10000,
            wind = Wind(
                speed = 12.4f,
                direction = 2
            ),
            clouds = Clouds(coverage = 10),
            rain = Rain(
                amount = 2.5f
            ),
            snow = Snow(
                amount = 2.5f
            ),
            dataCalculation = 1727377390,
            sunTime = SunTime(
                country = "US",
                sunRiseTimestamp = 1727353131,
                sunSetTimestamp = 1727396337
            ),
            timeZone = -18000,
            id = 4700168,
            name = "Guadalajara"
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
