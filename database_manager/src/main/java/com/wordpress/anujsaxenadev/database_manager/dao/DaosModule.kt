package com.wordpress.anujsaxenadev.database_manager.dao

import com.wordpress.anujsaxenadev.database_manager.MapDatabase
import com.wordpress.anujsaxenadev.database_manager.dao.daos.MapResourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaosModule {
    @Provides
    fun providesMapResourcesDao(
        database: MapDatabase,
    ): MapResourceDao = database.mapResourcesDao()
}