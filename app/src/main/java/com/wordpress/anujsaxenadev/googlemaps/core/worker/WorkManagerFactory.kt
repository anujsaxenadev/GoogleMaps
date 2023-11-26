package com.wordpress.anujsaxenadev.googlemaps.core.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.database_manager.helpers.map_helper.MapDatabaseHelper
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.impl.TimeCacheClearingStrategyWorker

class WorkManagerFactory(
    private val mapDatabaseHelper: MapDatabaseHelper,
    private val analyticsManager: AnalyticsManager
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return try {
            when(workerClassName) {
                TimeCacheClearingStrategyWorker::class.java.name ->
                    TimeCacheClearingStrategyWorker(appContext, workerParameters, mapDatabaseHelper)
                else ->{
                    analyticsManager.log(WorkerNotFoundException("Worker Not Found : $workerClassName"))
                    null
                }
            }
        }
        catch (e: Exception){
            analyticsManager.log(e)
            null
        }
    }
    private inner class WorkerNotFoundException(override val message: String): Throwable()
}