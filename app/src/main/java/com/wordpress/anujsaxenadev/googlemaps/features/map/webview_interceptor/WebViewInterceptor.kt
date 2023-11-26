package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

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
        request: WebResourceRequest
    ): WebResourceResponse? {
        return runBlocking {
            val result = mapViewModel.checkResourceAvailability(request)
            result.fold({
                it
            }, {
                if(it is MapViewModel.NetworkFetchingRequired){
                    WebResourceResponseWrapper(request, it.requestIdentifier, object : StreamInterruptionListener{
                        override suspend fun callNetworkAndGetData(
                            request: WebResourceRequest,
                            requestIdentifier: String
                        ): Result<WebResourceResponse> {
                            return mapViewModel.fetchDataFromNetworkAndSaveIt(request, requestIdentifier)
                        }
                    })
                }
                else{
                    super.shouldInterceptRequest(view, request)
                }
            })
        }
    }
}
