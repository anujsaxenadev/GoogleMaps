package com.wordpress.anujsaxenadev.googlemaps.features.map.repository.impl

import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import javax.inject.Inject

class MapDatabaseRepository @Inject constructor(private val mapDatabaseHelper: MapDatabaseHelper):
    MapRepository {
    override suspend fun checkResourceExist(resourceId: String): Result<Boolean> {
        return mapDatabaseHelper.checkResourceExist(resourceId)
    }

    override suspend fun storeResourceAndGetFileName(resourceId: String): Result<String> {
        return mapDatabaseHelper.storeResourceAndGetFileName(resourceId)
    }

}