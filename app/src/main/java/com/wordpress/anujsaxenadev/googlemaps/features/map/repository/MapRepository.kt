package com.wordpress.anujsaxenadev.googlemaps.features.map.repository

interface MapRepository{
    suspend fun checkResourceExist(resourceId: String): Result<Boolean>
    suspend fun storeResourceAndGetFileName(resourceId: String): Result<String>
}