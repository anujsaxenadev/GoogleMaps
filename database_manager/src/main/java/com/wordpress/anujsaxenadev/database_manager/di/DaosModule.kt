package com.wordpress.anujsaxenadev.database_manager.di

import com.wordpress.anujsaxenadev.database_manager.dao.MapResourceDao
import com.wordpress.anujsaxenadev.database_manager.databases.MapDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    internal fun providesMapResourcesDao(
        database: MapDatabase,
    ): MapResourceDao = database.mapResourcesDao()
}