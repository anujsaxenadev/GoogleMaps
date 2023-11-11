package com.wordpress.anujsaxenadev.network_manager.impl

import android.webkit.WebResourceRequest
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.models.Response
import kotlinx.coroutines.Dispatchers
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class OkHttpNetworkManager @Inject constructor(private val okHttpClient: OkHttpClient): NetworkManager {
    override suspend fun processRequest(request: WebResourceRequest): Result<Response> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            val requestBuilder = Request.Builder()
                .url(request.url.toString())
                .headers(request.requestHeaders.toHeaders())

            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), request.url.toString())
            when(request.method){
                "GET" -> requestBuilder.get()
                "POST" -> requestBuilder.post(requestBody)
                "PUT" -> requestBuilder.put(requestBody)
                "DELETE" -> requestBuilder.delete()
            }

            val response = okHttpClient.newCall(requestBuilder.build()).execute()
            Response(response.body?.byteStream(), HashMap(response.headers.toMap()))
        }
    }
}