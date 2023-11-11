package com.wordpress.anujsaxenadev.googlemaps.core

import android.webkit.WebResourceRequest
import android.webkit.WebSettings

fun WebSettings.applyMapSettings(){
    javaScriptEnabled = true
    javaScriptCanOpenWindowsAutomatically = true
    domStorageEnabled = true
    cacheMode = WebSettings.LOAD_NO_CACHE
}

fun WebResourceRequest.getUniqueIdentifier(): String{
    return url.hashCode().toString()
}

fun WebResourceRequest.checkIfMapTileRequest(): Boolean{
    return url.toString().startsWith("https://maps.googleapis.com/maps/vt")
}