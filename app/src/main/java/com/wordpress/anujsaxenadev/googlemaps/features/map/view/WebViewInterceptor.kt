package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WebViewInterceptor(
    private val mapViewModel: MapViewModel
): WebViewClient() {
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        Log.e("anuj-log-called", request?.url.toString())
        val result = ioScope.async{
            Log.e("anuj-log-started", request?.url.toString())
            delay((1..5000L).random())
            mapViewModel.checkResourceAvailability(request).fold({
                Log.e("anuj-log-was-cached", request?.url.toString())
                it
            }, {
                Log.e("anuj-log-was-not-called", request?.url.toString())
                super.shouldInterceptRequest(view, request)
            })
        }
        return runBlocking {
            Log.e("anuj-log-awaited", request?.url.toString())
            result.await()
        }
    }
}