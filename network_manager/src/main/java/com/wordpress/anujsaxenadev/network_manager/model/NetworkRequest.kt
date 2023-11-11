package com.wordpress.anujsaxenadev.network_manager.model

import com.wordpress.anujsaxenadev.network_manager.impl.Method
import com.wordpress.anujsaxenadev.network_manager.impl.RequestHeaders
import com.wordpress.anujsaxenadev.network_manager.impl.URL

data class NetworkRequest(
    val url: URL,
    val method: Method,
    val headers: RequestHeaders,
    val contentType: String
)