package com.wordpress.anujsaxenadev.googlemaps.core

import android.webkit.WebResourceRequest

fun WebResourceRequest.getUniqueIdentifier(): String{
    return url.hashCode().toString()
}

fun WebResourceRequest.checkIfMapTileRequest(): Boolean{
    return url.toString().startsWith("https://maps.googleapis.com/maps/vt")
}