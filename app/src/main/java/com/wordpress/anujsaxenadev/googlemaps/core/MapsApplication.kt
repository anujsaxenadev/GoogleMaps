package com.wordpress.anujsaxenadev.googlemaps.core

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.wordpress.anujsaxenadev.googlemaps.core.worker.WorkManagerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MapsApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var managerFactory: WorkManagerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(managerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
    }
}