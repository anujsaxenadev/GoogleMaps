package com.wordpress.anujsaxenadev.googlemaps.core

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MapsApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var configuration: Configuration
    override fun getWorkManagerConfiguration(): Configuration {
        return configuration
    }
}