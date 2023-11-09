package com.wordpress.anujsaxenadev.network_manager

import com.wordpress.anujsaxenadev.network_manager.models.Response


interface NetworkManager {
    suspend fun doGetRequest(url: String): Result<Response>
}