package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentEncoding
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.InputStream

class WebResourceResponseWrapper(
    private val request: WebResourceRequest,
    private val requestIdentifier: String,
    private val streamInterruptionListener: StreamInterruptionListener
) :
    WebResourceResponse(
        request.getContentType(),
        request.getContentEncoding(),
        null
    ) {

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

    override fun getData(): InputStream {
        return InputStreamWrapper()
    }

    private inner class InputStreamWrapper : InputStream() {
        override fun read(): Int {
            runBlocking {
                fetchingJob.await()
            }
            return inputStream?.read() ?: 0
        }
    }
}