package com.wordpress.anujsaxenadev.database_manager.di

import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HelperModule {
    @Binds
    internal abstract fun bindsMapDatabaseHelper(
        mapDatabaseHelperImpl: MapDatabaseHelperImpl
    ): MapDatabaseHelper
}