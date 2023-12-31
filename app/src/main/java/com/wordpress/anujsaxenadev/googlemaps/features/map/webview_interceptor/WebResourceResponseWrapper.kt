package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentEncoding
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getMimeType
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.InputStream

internal class WebResourceResponseWrapper(
    private val request: WebResourceRequest,
    private val requestIdentifier: String,
    private val streamInterruptionListener: StreamInterruptionListener
) :
    WebResourceResponse(
        request.getContentType(),
        request.getContentEncoding(),
        null
    ) {

    private var webResponse: WebResourceResponse? = null
    private var fetchingJob = ioScope.async {
        try {
            val response = streamInterruptionListener.callNetworkAndGetData(request, requestIdentifier).getOrNull()
            if(response != null){
                webResponse = response
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
            return webResponse?.data?.read() ?: 0
        }
    }

    // Overriding Other functions
    override fun getStatusCode(): Int {
        return if(webResponse == null){
            super.getStatusCode()
        }
        else{
            webResponse!!.statusCode
        }
    }

    override fun getMimeType(): String {
        return if(webResponse == null){
            request.getMimeType() ?: ""
        }
        else{
            webResponse!!.mimeType
        }
    }

    override fun getEncoding(): String {
        return if(webResponse == null){
            request.getContentEncoding() ?: ""
        }
        else{
            webResponse!!.encoding
        }
    }
}