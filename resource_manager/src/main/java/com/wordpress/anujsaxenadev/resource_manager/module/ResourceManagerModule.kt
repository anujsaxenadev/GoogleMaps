package com.wordpress.anujsaxenadev.resource_manager.module

import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import com.wordpress.anujsaxenadev.resource_manager.impl.AndroidInternalStorageResourceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceManagerModule {
    @Binds
    internal abstract fun bindsResourceManager(androidInternalStorageResourceManager: AndroidInternalStorageResourceManager): ResourceManager
}