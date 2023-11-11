package com.wordpress.anujsaxenadev.database_manager.module

import android.content.Context
import androidx.room.Room
import com.wordpress.anujsaxenadev.database_manager.MapDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesMapDatabase(
        @ApplicationContext context: Context,
    ): MapDatabase = Room.databaseBuilder(
        context,
        MapDatabase::class.java,
        "map-database",
    ).build()
}