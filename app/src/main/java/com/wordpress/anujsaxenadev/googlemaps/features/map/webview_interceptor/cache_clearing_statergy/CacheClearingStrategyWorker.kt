package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy

import androidx.lifecycle.LifecycleOwner

interface CacheClearingStrategyWorker {
    fun clearCache(owner: LifecycleOwner, completion: () -> Unit)
}