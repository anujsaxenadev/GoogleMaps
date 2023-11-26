package com.wordpress.anujsaxenadev.googlemaps.core.extensions

import android.webkit.MimeTypeMap
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import kotlinx.coroutines.Dispatchers

fun WebSettings.applyMapSettings(){
    javaScriptEnabled = true
    javaScriptCanOpenWindowsAutomatically = true
    domStorageEnabled = true
    cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
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

suspend fun WebResourceRequest.shouldOmitRequest(): Result<Boolean>{
    return runCatchingWithDispatcher(Dispatchers.Default){
        this.requestHeaders.keys.forEach {
            if(it.lowercase().contains("origin")){
                return@runCatchingWithDispatcher true
            }
        }
        false
    }
}

fun WebResourceRequest.getContentType(): String {
    // First, check if the content type is specified in the request headers.
    var contentType: String? = requestHeaders["Content-Type"]

    return if(contentType != null){
        contentType
    }
    else {
        // If the content type is not specified in the request headers, try to get it from the request method.
        contentType = when (method) {
            "POST" -> "application/x-www-form-urlencoded"
            "PUT" -> "application/octet-stream"
            else -> "text/plain"
        }

        contentType
    }
}

fun WebResourceRequest.getMimeType(): String? {
    return try {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url.toString())
        if (extension != null) {
            val mime = MimeTypeMap.getSingleton()
            mime.getMimeTypeFromExtension(extension)
        }
        else{
            null
        }
    }
    catch (e: Throwable){
        null
    }
}

fun WebResourceRequest.getContentEncoding(): String?{
    return try {
        requestHeaders["content-encoding"]
    }
    catch (e: Throwable){
        null
    }
}