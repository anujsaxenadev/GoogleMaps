package com.wordpress.anujsaxenadev.googlemaps.webview_interceptor

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getUniqueIdentifier
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.shouldOmitRequest
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import kotlinx.coroutines.runBlocking

class OptimisedWebViewClient(
    private val resourceManager: ResourceManager,
    private val networkManager: NetworkManager,
    private val mapRepository: MapRepository
): WebViewClient() {
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        if(request != null){
            runBlocking {
                val resourceDigest = request.getUniqueIdentifier().fold({
                    it
                }, {
                    // Log Error
                    logException(it)
                    null
                })
                if(resourceDigest != null){
                    val doesResourceExist = mapRepository.checkResourceExist(resourceDigest).getOrDefault(false)
                    val resourceName = mapRepository.getResourceName(resourceDigest).getOrNull()
                    if(doesResourceExist && resourceName != null){
                        // Get Data From Cache
                        Log.e("anuj-log", "Cached")
                        val cacheResource = resourceManager.getResourceReference(resourceName).fold({
                            it
                        }, {
                            logException(it)
                            null
                        })

                        if(cacheResource != null){
                            WebResourceResponse(
                                request.requestHeaders["content-type"],
                                request.requestHeaders["content-encoding"],
                                cacheResource
                            )
                        }
                        else{
                            // Log Error
                            super.shouldInterceptRequest(view, request)
                        }
                    }
                    else{
                        Log.e("anuj-log", "Network")
                        // Do Network Call And Save Resource
                        val shouldOmitRequest = request.shouldOmitRequest().fold({
                            it
                        }, {
                            logException(it)
                            true
                        })
                        if(shouldOmitRequest){
                            super.shouldInterceptRequest(view, request)
                        }
                        else{
                            val response = networkManager.processRequest(request).fold({
                                it
                            },{
                                logException(it)
                                null
                            })

                            val resourceIdName = mapRepository.storeResourceAndGetFileName(resourceDigest).fold({
                                it
                            },{
                                logException(it)
                                null
                            })
                            if(response?.response != null && resourceIdName != null){
                                val cacheResource = resourceManager.saveResourceAndGetReference(resourceIdName, response.response!!).fold({
                                    it
                                },{
                                    logException(it)
                                    null
                                })
                                if(cacheResource != null){
                                    WebResourceResponse(
                                        response.headers["content-type"],
                                        response.headers["content-encoding"],
                                        cacheResource
                                    )
                                }
                                else{
                                    super.shouldInterceptRequest(view, request)
                                }
                            }
                            else{
                                super.shouldInterceptRequest(view, request)
                            }
                        }
                    }
                }
                else{
                    super.shouldInterceptRequest(view, request)
                }
            }
        }
        return super.shouldInterceptRequest(view, request)
    }

    private fun logException(throwable: Throwable){
        // Log Exception
        Log.e("anuj-log", throwable.message, throwable)
    }
}