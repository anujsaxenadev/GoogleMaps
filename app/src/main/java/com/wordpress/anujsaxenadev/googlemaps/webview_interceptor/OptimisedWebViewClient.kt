package com.wordpress.anujsaxenadev.googlemaps.webview_interceptor

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.file_manager.FileManager
import com.wordpress.anujsaxenadev.googlemaps.core.checkIfMapTileRequest
import com.wordpress.anujsaxenadev.googlemaps.core.getUniqueIdentifier
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
            return runBlocking(Dispatchers.IO) {
                val isCached = fileManager.fileExists(request.getUniqueIdentifier()).getOrDefault(false)
                if(isCached){
                    Log.e("OptimisedWebViewClient: ", "Cached")
                    val response = networkManager.doGetRequest(request.url.toString())
                    WebResourceResponse(
                        response.header("content-type", "image/*"),
                        response.header("content-encoding", "utf-8"),
                        if(response.body != null){
                            fileManager.saveDataAndReturnStreamReference(request.getUniqueIdentifier(), response.body!!.byteStream()).getOrNull()
                        }
                        else{
                            null
                        }
                    )
                }
                else{
                    Log.e("OptimisedWebViewClient: ", "Not Cached")
                    WebResourceResponse(
                        "image/*",
                        "UTF-8",
                        FileInputStream(fileManager.createNewFile(request.getUniqueIdentifier()).getOrNull())
                    )
                }
            }

        }
        else{
            return super.shouldInterceptRequest(view, request)
        }
    }
}