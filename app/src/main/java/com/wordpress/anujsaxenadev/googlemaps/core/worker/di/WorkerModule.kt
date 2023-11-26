package com.wordpress.anujsaxenadev.googlemaps.core.worker.di

import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.core.worker.WorkManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WorkerModule {
    @Provides
    @Singleton
    internal fun providesWorkManager(
        @ApplicationContext applicationContext: Context
    ): WorkManager {
        return WorkManager.getInstance(applicationContext)
    }

    @Provides
    @Singleton
    fun providesWorkManagerFactory(
        mapDatabaseHelper: MapDatabaseHelper,
        analyticsManager: AnalyticsManager
    ): WorkManagerFactory {
        return WorkManagerFactory(mapDatabaseHelper, analyticsManager)
    }

    @Provides
    @Singleton
    fun providesWorkerConfiguration(
        managerFactory: WorkManagerFactory
    ): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(managerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
    }
}