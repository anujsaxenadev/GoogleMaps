package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class WebViewInterceptor(
    private val mapViewModel: MapViewModel
): WebViewClient() {
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return runBlocking {
            Log.e("anuj-log-called", request?.url.toString())
            delay((1..10).random() * 1000L)
            mapViewModel.checkResourceAvailability(request).fold({
                Log.e("anuj-log-resource-available", request?.url.toString())
                it
            }, {
                Log.e("anuj-log-resource-not-available", request?.url.toString())
                super.shouldInterceptRequest(view, request)
            })
        }
    }
}