package com.wordpress.anujsaxenadev.googlemaps.features.map.repository.module

import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.impl.MapDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapRepositoryModule {
    @Binds
    abstract fun bindsMapRepository(
        mapDatabaseRepository: MapDatabaseRepository
    ): MapRepository
}