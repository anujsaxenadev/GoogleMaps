package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse

interface StreamInterruptionListener{
    suspend fun callNetworkAndGetData(request: WebResourceRequest, requestIdentifier: String): Result<WebResourceResponse>
}