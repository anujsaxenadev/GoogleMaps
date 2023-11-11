package com.wordpress.anujsaxenadev.network_manager

import android.webkit.WebResourceRequest
import com.wordpress.anujsaxenadev.network_manager.models.Response


interface NetworkManager {
    /**
     * @param request takes [WebResourceRequest] which comes from a Webview
     *
     * @return [Result] of [Response] - if able to get the response from server - [Throwable] if any Error happens during this process.
     */
    suspend fun processRequest(request: WebResourceRequest): Result<Response>
}