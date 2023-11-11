package com.wordpress.anujsaxenadev.googlemaps.features.map.repository

interface MapRepository{
    suspend fun checkResourceExist(resourceId: String): Result<Boolean>
    suspend fun getResourceName(resourceId: String): Result<String>
    suspend fun storeResourceAndGetFileName(resourceId: String): Result<String>
}