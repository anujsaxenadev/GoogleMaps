package com.wordpress.anujsaxenadev.resource_manager

import com.wordpress.anujsaxenadev.resource_manager.impl.ResourceName
import java.io.InputStream

interface ResourceManager {

    /**
     * @param resourceName - Name of the Resource that need to be stored.
     * @param response - [InputStream] stream reference to the input resource that need to be saved.
     *
     * @return [Result] of [InputStream] - stream reference of the saved resource or [Throwable] - if there is some error happened in copy process.
     */
    suspend fun saveResourceAndGetReference(resourceName: ResourceName, response: InputStream): Result<InputStream>

    /**
     * @param resourceName - Name of the Resource.
     *
     * @return [Result] of [InputStream] - stream reference to the resource or [Throwable] if there is some error happened. like - resource does not exist
     */
    suspend fun getResourceReference(resourceName: ResourceName): Result<InputStream>

    /**
     * @param resourceName - Name of the Resource to delete
     *
     * @return [Result] of [Unit] - if able to delete file or [Throwable] if there is some error happened. like - resource does not exist
     */
    suspend fun removeResource(resourceName: ResourceName): Result<Unit>
}