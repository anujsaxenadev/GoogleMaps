package com.wordpress.anujsaxenadev.network_manager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object OkHttpModule {
    @Provides
    @Singleton
    internal fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
}