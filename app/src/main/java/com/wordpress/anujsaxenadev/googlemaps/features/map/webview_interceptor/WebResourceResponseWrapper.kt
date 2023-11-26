package com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentEncoding
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
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

    override fun getData(): InputStream {
        return InputStreamWrapper(request, requestIdentifier, streamInterruptionListener)
    }
}