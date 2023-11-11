package com.wordpress.anujsaxenadev.network_manager

import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse


interface NetworkManager {
    /**
     * @param request takes [NetworkRequest] which comes from a Webview
     *
     * @return [Result] of [NetworkResponse] - if able to get the response from server - [Throwable] if any Error happens during this process.
     */
    suspend fun processRequest(request: NetworkRequest): Result<NetworkResponse>
}