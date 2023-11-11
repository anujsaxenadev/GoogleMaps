package com.wordpress.anujsaxenadev.network_manager.extensions

import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders

internal fun NetworkRequest.getHeaders(): Headers {
    return headers.toHeaders()
}
