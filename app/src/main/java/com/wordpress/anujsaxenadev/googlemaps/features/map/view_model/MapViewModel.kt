package com.wordpress.anujsaxenadev.googlemaps.features.map.view_model

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebViewClient
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.common.extensions.lazySync
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getUniqueIdentifier
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.shouldOmitRequest
import com.wordpress.anujsaxenadev.googlemaps.features.map.states.MapState
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.WebViewInterceptor
import com.wordpress.anujsaxenadev.googlemaps.features.map.webview_interceptor.cache_clearing_statergy.CacheClearingStrategyWorker
import com.wordpress.anujsaxenadev.network_manager.model.NetworkRequest
import com.wordpress.anujsaxenadev.network_manager.model.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withTimeout
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val analyticsManager: AnalyticsManager,
    private val cacheClearingStrategyWorker: CacheClearingStrategyWorker
): ViewModel() {
    private val _cacheClearingState = MutableStateFlow<MapState>(MapState.Loading)
    val cacheClearingState = _cacheClearingState.asStateFlow()

    private val webViewInterceptor by lazySync {
        WebViewInterceptor(this)
    }

    companion object{
        private const val CANCELLATION_TIMEOUT = 500L
    }

    fun getWebViewClient(): WebViewClient{
        return webViewInterceptor
    }

    fun clearCache(lifecycleOwner: LifecycleOwner){
        cacheClearingStrategyWorker.clearCache(lifecycleOwner){
            _cacheClearingState.value = MapState.CacheCleared
        }
    }

    suspend fun checkResourceAvailability(request: WebResourceRequest?): Result<WebResourceResponse>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            withTimeout(CANCELLATION_TIMEOUT){
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
                throw NetworkFetchingRequired(requestUniqueIdentifier)
            }
        }
    }

    suspend fun fetchDataFromNetworkAndSaveIt(request: WebResourceRequest, requestUniqueIdentifier: String): Result<WebResourceResponse>{
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

    fun logException(throwable: Throwable){
        analyticsManager.log(throwable)
    }

    private inner class ResourceFetchingFailed: Exception()
    inner class NetworkFetchingRequired(val requestIdentifier: String): Exception()
}
