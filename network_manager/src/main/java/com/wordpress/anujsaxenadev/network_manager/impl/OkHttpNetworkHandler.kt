package com.wordpress.anujsaxenadev.network_manager.impl

import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class OkHttpNetworkHandler: NetworkManager {
    private val networkClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    override suspend fun doGetRequest(url: String): Response {
        return withContext(Dispatchers.IO){
            val request = Request.Builder().url(url).build()
            networkClient.newCall(request).execute()
        }
    }
}