package com.wordpress.anujsaxenadev.network_manager.impl

import com.wordpress.anujsaxenadev.common.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.models.Response
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class OkHttpNetworkManager @Inject constructor(): NetworkManager {
    companion object{
        private const val bodyNull = "Body of Request is Null"
    }
    private val networkClient: OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient()
    }

    override suspend fun doGetRequest(url: URL): Result<Response> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            val request = Request.Builder().url(url).build()
            val response = networkClient.newCall(request).execute()
            val headerMap = HashMap<String, String>()
            for((key, value) in response.headers){
                headerMap[key] = value
            }
            if(response.body != null){
                Response(response.body!!.byteStream(), headerMap)
            }
            else{
                throw Exception(bodyNull)
            }
        }
    }
}