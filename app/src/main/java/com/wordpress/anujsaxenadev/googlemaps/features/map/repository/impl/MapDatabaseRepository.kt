package com.wordpress.anujsaxenadev.googlemaps.features.map.repository.impl

import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse
import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import java.io.InputStream
import javax.inject.Inject

class MapDatabaseRepository @Inject constructor(
    private val mapDatabaseHelper: MapDatabaseHelper,
    private val networkManager: NetworkManager,
    private val resourceManager: ResourceManager):
    MapRepository {
    override suspend fun checkResourceExist(resourceId: String): Result<Boolean> {
        return mapDatabaseHelper.checkResourceExist(resourceId)
    }

    override suspend fun storeResourceAndGetFileName(resourceId: String): Result<String> {
        return mapDatabaseHelper.storeResourceAndGetFileName(resourceId)
    }

    override suspend fun processRequest(request: NetworkRequest): Result<NetworkResponse> {
        return networkManager.processRequest(request)
    }

    override suspend fun saveResourceAndGetReference(
        resourceName: String,
        response: InputStream
    ): Result<InputStream> {
        return resourceManager.saveResourceAndGetReference(resourceName, response)
    }

    override suspend fun getResourceReference(resourceName: String): Result<InputStream> {
        return resourceManager.getResourceReference(resourceName)
    }

    override suspend fun getResourceName(resourceId: String): Result<String>{
        return mapDatabaseHelper.getResourceName(resourceId)
    }

}