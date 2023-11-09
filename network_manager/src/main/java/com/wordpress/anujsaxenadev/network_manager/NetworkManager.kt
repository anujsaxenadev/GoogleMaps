package com.wordpress.anujsaxenadev.network_manager

import okhttp3.Response

interface NetworkManager {
    suspend fun doGetRequest(url: String): Response
}