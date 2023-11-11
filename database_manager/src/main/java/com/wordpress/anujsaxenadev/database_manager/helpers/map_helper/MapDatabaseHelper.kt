package com.wordpress.anujsaxenadev.database_manager.helpers.map_helper

interface MapDatabaseHelper{
    suspend fun checkResourceExist(resourceId: String): Result<Boolean>
    suspend fun getResourceName(resourceId: String): Result<String>
    suspend fun storeResourceAndGetFileName(resourceId: String): Result<String>
}