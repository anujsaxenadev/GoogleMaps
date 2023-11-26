package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.impl

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getTodayTimeStamp
import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
internal class TimeCacheClearingStrategyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mapDatabaseHelper: MapDatabaseHelper,
    private val resourceManager: ResourceManager,
    private val analyticsManager: AnalyticsManager
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){

            addAnimationDelay()

            val timestamp = getTodayTimeStamp()

            deleteFilesFromCache(timestamp)

            // Deleting Resource From DB
            val result = mapDatabaseHelper.removeResourceBefore(timestamp)

            result.fold({
                Result.success()
            }, {
                analyticsManager.log(it)
                Result.failure()
            })
        }
    }

    // Added default Delay for smooth Animation
    private suspend fun addAnimationDelay(){
        delay(1000)
    }

    private suspend fun deleteFilesFromCache(timestamp: Long){
        val cacheResourceList = mapDatabaseHelper.getResourcesBefore(timestamp)
            .fold({
                  it
            }, {
                analyticsManager.log(it)
                null
            })
        for(file in cacheResourceList ?: ArrayList()){
            resourceManager.removeResource(file)
        }
    }
}