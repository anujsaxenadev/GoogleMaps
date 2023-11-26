package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.di

import androidx.work.WorkManager
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.CacheClearingStrategyWorker
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.impl.CacheClearingStrategyWorkerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheClearingStrategyModule {
    @Provides
    @Singleton
    fun providesCacheClearingStrategyWorker(
        workManager: WorkManager
    ): CacheClearingStrategyWorker{
        return CacheClearingStrategyWorkerImpl(workManager)
    }
}