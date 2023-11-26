package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.impl

import androidx.lifecycle.LifecycleOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.wordpress.anujsaxenadev.common.extensions.lazySync
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.CacheClearingStrategyWorker
import javax.inject.Inject

internal class CacheClearingStrategyWorkerImpl @Inject constructor(private val workManager: WorkManager):
    CacheClearingStrategyWorker {
    private val request by lazySync {
        OneTimeWorkRequestBuilder<TimeCacheClearingStrategyWorker>().build()
    }
    override fun clearCache(owner: LifecycleOwner, completion: () -> Unit){
        workManager.getWorkInfoByIdLiveData(request.id).observe(owner){
            if(it.state == WorkInfo.State.SUCCEEDED || it.state == WorkInfo.State.FAILED || it.state == WorkInfo.State.CANCELLED) {
                completion()
            }
        }
        workManager.enqueue(request)
    }
}