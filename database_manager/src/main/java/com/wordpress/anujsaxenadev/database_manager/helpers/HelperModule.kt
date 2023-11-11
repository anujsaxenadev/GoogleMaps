package com.wordpress.anujsaxenadev.database_manager.helpers

import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HelperModule {
    @Binds
    abstract fun bindsMapDatabaseHelper(
        mapDatabaseHelperImpl: MapDatabaseHelperImpl
    ): MapDatabaseHelper
}