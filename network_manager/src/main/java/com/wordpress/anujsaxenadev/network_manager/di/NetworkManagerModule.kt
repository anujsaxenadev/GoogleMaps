package com.wordpress.anujsaxenadev.network_manager.di

import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.impl.OkHttpNetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    internal fun providesNetworkManager(okHttpClient: OkHttpClient): NetworkManager = OkHttpNetworkManager(okHttpClient)
}