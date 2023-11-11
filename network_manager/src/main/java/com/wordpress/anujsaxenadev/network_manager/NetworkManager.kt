package com.wordpress.anujsaxenadev.network_manager

import com.wordpress.anujsaxenadev.network_manager.impl.URL
import com.wordpress.anujsaxenadev.network_manager.models.Response


interface NetworkManager {
    /**
     * @param url - takes URL for get Network Call from OKHttp.
     *
     * @return [Result] of [Response] - if able to get the response from server - [Throwable] if any Error happens during this process.
     */
    suspend fun doGetRequest(url: URL): Result<Response>
}