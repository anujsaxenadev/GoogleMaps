package com.wordpress.anujsaxenadev.network_manager.impl

import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpNetworkHandler @Inject constructor(): NetworkManager {
    private val networkClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    override suspend fun doGetRequest(url: String): Result<Response> {
        return withContext(Dispatchers.IO){
            runCatching {
                try {
                    val response = networkClient.newCall(Request.Builder().url(url).build()).execute()
                    val headerMap = HashMap<String, String>()
                    for((key, value) in response.headers){
                        headerMap[key] = value
                    }
                    if(response.body != null){
                        Response(response.body!!.byteStream(), headerMap)
                    }
                    else{
                        throw Exception("Body is Null")
                    }
                }
                catch (e: Throwable){
                    throw e
                }
            }
        }
    }
}