package com.wordpress.anujsaxenadev.googlemaps.core.extensions

import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import kotlinx.coroutines.Dispatchers

fun WebSettings.applyMapSettings(){
    javaScriptEnabled = true
    javaScriptCanOpenWindowsAutomatically = true
    domStorageEnabled = true
    cacheMode = WebSettings.LOAD_NO_CACHE
}

suspend fun WebResourceRequest.getUniqueIdentifier(): Result<String>{
    return runCatchingWithDispatcher(Dispatchers.Default) {
        val identifierBuilder = StringBuilder()
        val separator = '-'

        identifierBuilder.apply {
            append(url.getUniqueIdentifier().getOrDefault(""))

            append(separator).append(method)

            append(separator).append(isRedirect)

            append(separator).append(isForMainFrame)

            append(separator).append(hasGesture())

            for (headerEntry in requestHeaders.entries) {
                append(separator).append(headerEntry.key.lowercase()).append(separator)
                    .append(headerEntry.value)
            }
        }
        identifierBuilder.toString()
    }
}

fun WebResourceRequest.checkIfMapTileRequest(): Boolean{
    return url.toString().startsWith("https://maps.googleapis.com/maps/vt")
}