package com.wordpress.anujsaxenadev.googlemaps.features.map.view_model

import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.wordpress.anujsaxenadev.common.extensions.lazySync
import com.wordpress.anujsaxenadev.googlemaps.features.map.repository.MapRepository
import com.wordpress.anujsaxenadev.googlemaps.webview_interceptor.OptimisedWebViewClient
import com.wordpress.anujsaxenadev.network_manager.NetworkManager
import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val networkManager: NetworkManager,
    private val mapRepository: MapRepository
): ViewModel() {
    private val optimisedWebViewClient by lazySync {
        OptimisedWebViewClient(resourceManager, networkManager,mapRepository)
    }

    fun getWebViewClient(): WebViewClient{
        return optimisedWebViewClient
    }
}
