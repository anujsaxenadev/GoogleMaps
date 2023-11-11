package com.wordpress.anujsaxenadev.googlemaps.webview_interceptor

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.common.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.file_manager.FileManager
import com.wordpress.anujsaxenadev.googlemaps.core.checkIfMapTileRequest
import com.wordpress.anujsaxenadev.googlemaps.core.getUniqueIdentifier
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.network_manager.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.FileInputStream

class OptimisedWebViewClient(
    private val fileManager: FileManager,
    private val networkManager: NetworkManager
): WebViewClient() {
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        // Is Map Tiles
        if(request?.checkIfMapTileRequest() == true){
            return runBlocking {
                runCatchingWithDispatcher(Dispatchers.IO){
                    val isCached = fileManager.fileExists(request.getUniqueIdentifier()).getOrDefault(false)
                    if(isCached){
                        Log.e("OptimisedWebViewClient: ", "Cached")
                        WebResourceResponse(
                            "image/*",
                            "UTF-8",
                            FileInputStream(fileManager.createNewFile(request.getUniqueIdentifier()).getOrNull())
                        )
                    }
                    else{
                        Log.e("OptimisedWebViewClient: ", "Not Cached")
                        val response: Result<Response> = networkManager.doGetRequest(request.url.toString())
                        response.fold({
                            WebResourceResponse(
                                it.headers["content-type"],
                                it.headers["content-encoding"],
                                fileManager.saveDataAndReturnStreamReference(request.getUniqueIdentifier(), it.response).getOrNull()
                            )
                        }, {
                            throw it
                        })
                    }
                }.getOrNull()
            }
        }
        else{
            return super.shouldInterceptRequest(view, request)
        }
    }
}