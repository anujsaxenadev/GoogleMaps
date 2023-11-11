package com.wordpress.anujsaxenadev.googlemaps.features.map.repository

import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse
import java.io.InputStream

interface MapRepository{
    suspend fun checkResourceExist(resourceId: String): Result<Boolean>
    suspend fun getResourceName(resourceId: String): Result<String>
    suspend fun storeResourceAndGetFileName(resourceId: String): Result<String>
    suspend fun processRequest(request: NetworkRequest): Result<NetworkResponse>
    suspend fun saveResourceAndGetReference(resourceName: String, response: InputStream): Result<InputStream>
    suspend fun getResourceReference(resourceName: String): Result<InputStream>
}