package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import com.wordpress.anujsaxenadev.common.extensions.lazySync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal val ioScope: CoroutineScope by lazySync {
    CoroutineScope(Dispatchers.IO + SupervisorJob())
}