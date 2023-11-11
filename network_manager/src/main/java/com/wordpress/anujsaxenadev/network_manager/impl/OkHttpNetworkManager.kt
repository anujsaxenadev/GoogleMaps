package com.wordpress.anujsaxenadev.network_manager.impl

import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.extensions.getHeaders
import com.wordpress.anujsaxenadev.network_manager.extensions.getMethod
import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse
import com.wordpress.anujsaxenadev.network_manager.model.RequestMethods
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class OkHttpNetworkManager @Inject constructor(private val okHttpClient: OkHttpClient): NetworkManager {
    override suspend fun processRequest(request: NetworkRequest): Result<NetworkResponse> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            val networkRequestBuilder = Request.Builder()
                .url(request.url)
                .headers(request.getHeaders())

            val requestBody = request.url.toRequestBody(request.contentType.toMediaTypeOrNull())
            when(request.method.getMethod()){
                RequestMethods.Delete -> networkRequestBuilder.delete()
                RequestMethods.Get -> networkRequestBuilder.get()
                RequestMethods.Post -> networkRequestBuilder.post(requestBody)
                RequestMethods.Put -> networkRequestBuilder.put(requestBody)
            }

            val response = okHttpClient.newCall(networkRequestBuilder.build()).execute()
            NetworkResponse(response.body?.byteStream(), HashMap(response.headers.toMap()))
        }
    }
}