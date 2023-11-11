package com.wordpress.anujsaxenadev.resource_manager.di

import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import com.wordpress.anujsaxenadev.resource_manager.impl.AndroidInternalStorageResourceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ResourceManagerModule {
    @Binds
    internal abstract fun bindsResourceManager(androidInternalStorageResourceManager: AndroidInternalStorageResourceManager): ResourceManager
}