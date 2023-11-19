package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
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
        val channel = Channel<WebResourceResponse?>()

        // Launch a coroutine to handle the requests
        ioScope.launch {
            val result = mapViewModel.checkResourceAvailability(request)

            result.fold({
                channel.send(result.getOrNull())
            },{
                channel.send(super.shouldInterceptRequest(view, request))
            })
        }

        // Receive the result from the channel
        return runBlocking {
            channel.consume {
                receive()
            }
        }
    }
}