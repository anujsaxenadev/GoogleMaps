package com.wordpress.anujsaxenadev.database_manager.helpers.map_helper

import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.database_manager.databases.MapDatabase
import com.wordpress.anujsaxenadev.database_manager.model.MapResource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

internal class MapDatabaseHelperImpl @Inject constructor(private val database: MapDatabase):
    MapDatabaseHelper {
    override suspend fun checkResourceExist(resourceId: String): Result<Boolean> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getResourceByResourceId(resourceId).isSuccess
        }
    }

    override suspend fun getResourceName(resourceId: String): Result<String>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            getResourceByResourceId(resourceId).fold({
                it.getResourceName()
            }, {
                throw it
            })
        }
    }

    private suspend fun getResourceByResourceId(resourceId: String): Result<MapResource>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            database.mapResourcesDao().getResourceByResourceId(resourceId)
                ?: throw Exception("Resource Not Found")
        }
    }

    private suspend fun insertResource(mapResource: MapResource): Result<Unit>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            database.mapResourcesDao().insert(mapResource)
        }
    }

    override suspend fun storeResourceAndGetFileName(resourceId: String): Result<String> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            insertResource(MapResource(resourceId, System.currentTimeMillis())).fold({
                getResourceByResourceId(resourceId).fold({
                    it.getResourceName()
                }, {
                    throw it
                })
            }, {
                throw it
            })
        }
    }

    override suspend fun removeResourceBefore(timestamp: Long): Result<Unit> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            database.mapResourcesDao().removeResourcesBefore(timestamp)
        }
    }

    override suspend fun getResourcesBefore(timestamp: Long): Result<List<String>> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            val resultList = ArrayList<String>()
            val result = database.mapResourcesDao().getResourcesBefore(timestamp)
            for(entries in result){
                resultList.add(entries.getResourceName())
            }
            resultList
        }
    }

}