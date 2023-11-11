package com.wordpress.anujsaxenadev.database_manager.helpers.map_helper

import com.wordpress.anujsaxenadev.common.models.Nothing
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.database_manager.MapDatabase
import com.wordpress.anujsaxenadev.database_manager.model.MapResource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MapDatabaseHelperImpl @Inject constructor(private val database: MapDatabase):
    MapDatabaseHelper {
    override suspend fun checkResourceExist(resourceId: String): Result<Boolean> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getResourceByResourceId(resourceId).isSuccess
        }
    }

    private suspend fun getResourceByResourceId(resourceId: String): Result<MapResource>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            database.mapResourcesDao().getResourceByResourceId(resourceId)
                ?: throw Exception("Resource Not Found")
        }
    }

    private suspend fun insertResource(mapResource: MapResource): Result<Nothing>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            if(database.mapResourcesDao().insert(mapResource) == 1L)
                Nothing()
            else
                throw Exception("Error In Saving Map Data")
        }
    }

    override suspend fun storeResourceAndGetFileName(resourceId: String): Result<String> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            insertResource(MapResource(resourceId)).fold({
                getResourceByResourceId(resourceId).fold({
                    it.resourceIndex.toString()
                }, {
                    throw it
                })
            }, {
                throw it
            })
        }
    }

}