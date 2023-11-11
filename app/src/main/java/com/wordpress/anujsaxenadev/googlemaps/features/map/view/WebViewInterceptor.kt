package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import kotlinx.coroutines.runBlocking

class WebViewInterceptor(
    private val mapViewModel: MapViewModel
): WebViewClient() {
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return runBlocking {
            mapViewModel.checkResourceAvailability(request).fold({
                it
            }, {
                super.shouldInterceptRequest(view, request)
            })
        }
    }
}