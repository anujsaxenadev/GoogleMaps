package com.wordpress.anujsaxenadev.googlemaps.features.map.view_model

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.common.extensions.lazySync
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getUniqueIdentifier
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.shouldOmitRequest
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.googlemaps.features.map.view.WebViewInterceptor
import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val analyticsManager: AnalyticsManager
): ViewModel() {
    private val webViewInterceptor by lazySync {
        WebViewInterceptor(this)
    }

    fun getWebViewClient(): WebViewClient{
        return webViewInterceptor
    }

    suspend fun checkResourceAvailability(request: WebResourceRequest?): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            if(request != null) {
                val requestUniqueIdentifier = request.getUniqueIdentifier().fold({
                    it
                }, {
                    logException(it)
                    null
                })
                if (requestUniqueIdentifier != null) {
                    checkResourceAvailabilityForUniqueIdentifier(
                        requestUniqueIdentifier,
                        request
                    ).fold(
                        {
                            it
                        },
                        {
                            throw it
                        })
                } else {
                    throw ResourceFetchingFailed()
                }
            }
            else{
                throw ResourceFetchingFailed()
            }
        }
    }

    private suspend fun checkResourceAvailabilityForUniqueIdentifier(requestUniqueIdentifier: String, request: WebResourceRequest): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            val doesResourceExist = mapRepository.checkResourceExist(requestUniqueIdentifier).getOrDefault(false)
            val resourceName = mapRepository.getResourceName(requestUniqueIdentifier).getOrNull()
            if(doesResourceExist && resourceName != null){
                // Get Data From Cache
                getResourceFromCache(resourceName, request).fold({
                    it
                },{
                    throw it
                })
            }
            else{
                // Do Network Call And Save Resource
                doNetworkCallAndSaveResource(request, requestUniqueIdentifier).fold({
                    it
                }, {
                    throw it
                })
            }
        }
    }

    private suspend fun getResourceFromCache(resourceName: String, request: WebResourceRequest): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            val cacheResource = mapRepository.getResourceReference(resourceName).fold({
                it
            }, {
                logException(it)
                null
            })

            if(cacheResource != null){
                generateResponse(request.requestHeaders, cacheResource)
            }
            else{
                throw ResourceFetchingFailed()
            }
        }
    }

    private suspend fun doNetworkCallAndSaveResource(request: WebResourceRequest, requestUniqueIdentifier: String): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            val shouldOmitRequest = request.shouldOmitRequest().fold({
                it
            }, {
                logException(it)
                true
            })
            if (shouldOmitRequest) {
                throw ResourceFetchingFailed()
            } else {
                fetchDataFromNetworkAndSaveIt(request, requestUniqueIdentifier).fold({
                    it
                }, {
                    throw it
                })
            }
        }
    }

    private suspend fun fetchDataFromNetworkAndSaveIt(request: WebResourceRequest, requestUniqueIdentifier: String): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            val response = mapRepository.processRequest(
                NetworkRequest(
                    url = request.url.toString(),
                    method = request.method,
                    headers = HashMap(request.requestHeaders),
                    contentType = request.getContentType()
                )
            )
                .fold({
                    it
                },{
                    logException(it)
                    null
                })

            val resourceIdName = mapRepository.storeResourceAndGetFileName(requestUniqueIdentifier).fold({
                it
            },{
                logException(it)
                null
            })

            val canSaveResource = response?.response != null && resourceIdName != null
            if(canSaveResource){
                saveResourceInCacheAndGetResponse(resourceIdName!!, response!!).fold({
                    it
                }, {
                    throw it
                })
            }
            else{
                throw ResourceFetchingFailed()
            }
        }
    }

    private suspend fun saveResourceInCacheAndGetResponse(resourceIdName: String, response: NetworkResponse): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            val cacheResource =
                mapRepository.saveResourceAndGetReference(resourceIdName, response.response!!)
                    .fold({
                        it
                    }, {
                        logException(it)
                        null
                    })
            if (cacheResource != null) {
                generateResponse(response.headers, cacheResource)
            } else {
                throw ResourceFetchingFailed()
            }
        }
    }

    private fun generateResponse(headers: Map<String, String>, data: InputStream?): WebResourceResponse {
        return WebResourceResponse(
            headers["content-type"],
            headers["content-encoding"],
            data
        )
    }

    private fun logException(throwable: Throwable){
        analyticsManager.log(throwable)
    }

    private inner class ResourceFetchingFailed: Exception()
}
