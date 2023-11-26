package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.InputStream

class InputStreamWrapper() : InputStream() {
    private var inputStream: InputStream? = null
    private var fetchingJob: Deferred<WebResourceResponse?>? = null

    private var request: WebResourceRequest? = null
    private var requestIdentifier: String? = null
    private var streamInterruptionListener: StreamInterruptionListener? = null

    constructor(
        request: WebResourceRequest,
        requestIdentifier: String,
        streamInterruptionListener: StreamInterruptionListener
    ) : this() {
        this.request = request
        this.requestIdentifier = requestIdentifier
        this.streamInterruptionListener = streamInterruptionListener
        fetchingJob = ioScope.async {
            streamInterruptionListener.callNetworkAndGetData(request, requestIdentifier).getOrNull()
        }
    }

    override fun read(): Int {
        return if(inputStream == null) {
            runBlocking {
                inputStream = fetchingJob?.await()?.data
            }
            inputStream?.read()
        }
        else{
            inputStream?.read()
        } ?: 0
    }
}