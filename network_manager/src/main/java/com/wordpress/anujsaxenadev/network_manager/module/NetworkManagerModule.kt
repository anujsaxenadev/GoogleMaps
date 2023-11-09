package com.wordpress.anujsaxenadev.network_manager.module

import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.impl.OkHttpNetworkHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkManagerModule {
    @Binds
    abstract fun bindsNetworkManager(okHttpNetworkHandler: OkHttpNetworkHandler): NetworkManager
}