package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.impl

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getTodayTimeStamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
internal class TimeCacheClearingStrategyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mapDatabaseHelper: MapDatabaseHelper
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            val result = mapDatabaseHelper.removeResourceBefore(getTodayTimeStamp())
            result.fold({
                Result.success()
            }, {
                Result.failure()
            })
        }
    }
}