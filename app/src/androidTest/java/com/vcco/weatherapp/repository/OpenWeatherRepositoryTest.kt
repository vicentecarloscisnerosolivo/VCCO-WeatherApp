package com.vcco.weatherapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcco.weatherapp.model.geocoding.GeocodeResponse
import com.vcco.weatherapp.model.weather.Clouds
import com.vcco.weatherapp.model.weather.Conditions
import com.vcco.weatherapp.model.weather.Coordinates
import com.vcco.weatherapp.model.weather.CurrentWeatherResponse
import com.vcco.weatherapp.model.weather.Rain
import com.vcco.weatherapp.model.weather.Snow
import com.vcco.weatherapp.model.weather.SunTime
import com.vcco.weatherapp.model.weather.Temperature
import com.vcco.weatherapp.model.weather.Wind
import com.vcco.weatherapp.model.zip.ZipResponse
import com.vcco.weatherapp.network.apiHelper.OpenWeatherApiHelper
import com.vcco.weatherapp.repositories.OpenWeatherRepository
import com.vcco.weatherapp.repositories.OpenWeatherRepositoryJava
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import retrofit2.Response

/**
 * Instrumented test for OpenWeatherRepository
 */
@RunWith(AndroidJUnit4::class)
class OpenWeatherRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var apiHelper: OpenWeatherApiHelper

    //Repositories
    lateinit var repository: OpenWeatherRepository
    lateinit var javaRepository: OpenWeatherRepositoryJava

    //models response
    lateinit var currentResponse: CurrentWeatherResponse
    lateinit var currentLocationResponse: List<GeocodeResponse>
    lateinit var zipResponse: ZipResponse

    /**
     * Used to sed data, init repositories, anda data
     * */
    @Before
    fun setUp() {
        currentResponse = CurrentWeatherResponse(
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
            name = "Irving"
        )
        currentLocationResponse = listOf(
            GeocodeResponse(
                longitude = -96.9489f,
                latitude = 32.814f,
                name = "Irving",
                localNames = mapOf("Guadalajara" to "en"),
                country = "US",
                state = "Texas"
            )
        )
        zipResponse = ZipResponse(
            zipCode = "75039",
            name = "Irving",
            longitude = -96.9489f,
            latitude = 32.814f,
            country = "US"
        )
        apiHelper = Mockito.mock(OpenWeatherApiHelper::class.java)
        repository = OpenWeatherRepository(apiHelper)
        javaRepository = OpenWeatherRepositoryJava(apiHelper)
    }


    @Test
    fun useGeCurrentWeatherRepositorySuccess() {
        runBlocking {
            Mockito.`when`(repository.getCurrentWeatherFromLocation("HomeTown", "metric"))
                .thenReturn(Observable.just(Response.success(currentResponse)))
            val response = repository.getCurrentWeatherFromLocation("HomeTown", "metric")
            val testObserver = TestObserver<Response<CurrentWeatherResponse>>()
            response.subscribe(testObserver)
            testObserver.assertComplete()
            testObserver.assertNoErrors()
        }
    }

    @Test
    fun userGetInfoFromLocation() {
        run {
            Mockito.`when`(javaRepository.getLocationInfoFromCoordinates(32.814f, -96.9489f))
                .thenReturn(Observable.just(Response.success(currentLocationResponse)))
            val response = javaRepository.getLocationInfoFromCoordinates(32.814f, -96.9489f)
            val testObserver = TestObserver<Response<List<GeocodeResponse>>>()
            response.subscribe(testObserver)
            testObserver.assertComplete()
            testObserver.assertNoErrors()
        }
    }

    @Test
    fun userGetZipCodeResponse() {
        run {
            Mockito.`when`(repository.getInfoFromZipCode("75039"))
                .thenReturn(Observable.just(Response.success(zipResponse)))
            val response = repository.getInfoFromZipCode("75039")
            val testObserver = TestObserver<Response<ZipResponse>>()
            response.subscribe(testObserver)
            testObserver.assertComplete()
            testObserver.assertNoErrors()
        }
    }

    @Test
    fun userGetCityNamesResponse() {
        run {
            Mockito.`when`(repository.getLocationFromName("Irving"))
                .thenReturn(Observable.just(Response.success(currentLocationResponse)))
            val response = repository.getLocationFromName("Irving")
            val testObserver = TestObserver<Response<List<GeocodeResponse>>>()
            response.subscribe(testObserver)
            testObserver.assertComplete()
            testObserver.assertNoErrors()
        }
    }
}

