package com.vcco.weatherapp.di.binds

import com.vcco.weatherapp.network.apiHelper.OpenWeatherApiHelper
import com.vcco.weatherapp.network.apiHelper.OpenWeatherApiHelperImpl
import com.vcco.weatherapp.repositories.OpenWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Abstract class used for bind Interfaces and Implementation
 */
/*
* continue the bridge implementation
* */

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    /**
     * Bind OpenWeatherApi interface and Implementation
     */
    @Binds
    abstract fun provideApiHelper(openWeatherApiHelperImpl: OpenWeatherApiHelperImpl): OpenWeatherApiHelper
}