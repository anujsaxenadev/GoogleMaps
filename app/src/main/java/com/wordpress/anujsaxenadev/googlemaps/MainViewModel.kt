package com.wordpress.anujsaxenadev.googlemaps

import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.wordpress.anujsaxenadev.file_manager.FileManager
import com.wordpress.anujsaxenadev.googlemaps.webview_interceptor.OptimisedWebViewClient
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val networkManager: NetworkManager
): ViewModel() {
    private val optimisedWebViewClient by lazy {
        OptimisedWebViewClient(fileManager, networkManager)
    }

    fun getWebViewClient(): WebViewClient{
        return optimisedWebViewClient
    }
}