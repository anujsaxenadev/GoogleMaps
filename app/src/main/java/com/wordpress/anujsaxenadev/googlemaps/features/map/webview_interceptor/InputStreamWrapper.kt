package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.InputStream

class InputStreamWrapper(
    private val request: WebResourceRequest,
    private val requestIdentifier: String,
    private val streamInterruptionListener: StreamInterruptionListener
) : InputStream() {
    private var inputStream: InputStream? = null
    private var fetchingJob = ioScope.async {
        try {
            val response = streamInterruptionListener.callNetworkAndGetData(request, requestIdentifier).getOrNull()
            if(response != null){
                inputStream = response.data
            }
        }
        catch (e: Throwable){
            streamInterruptionListener.logException(e)
        }
    }

    override fun read(): Int {
        runBlocking {
             fetchingJob.await()
        }
        return inputStream?.read() ?: 0
    }
}