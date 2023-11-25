package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentEncoding
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.getContentType
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.InputStream


class WebViewInterceptor(
    private val mapViewModel: MapViewModel
): WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest
    ): WebResourceResponse {
		return WebResourceResponseWrapper(request, object: StreamInterruptionListener{
			override suspend fun checkResourceAvailability(request: WebResourceRequest): Result<WebResourceResponse> {
				return mapViewModel.checkResourceAvailability(request)
			}
		})
    }
}

interface StreamInterruptionListener{
	suspend fun checkResourceAvailability(request: WebResourceRequest): Result<WebResourceResponse>
}

internal class WebResourceResponseWrapper(
    private val request: WebResourceRequest,
	private val streamListener: StreamInterruptionListener
) :
    WebResourceResponse(
		request.getContentType(),
		request.getContentEncoding(),
		null
	) {

    override fun getData(): InputStream {
        return InputStreamWrapper(request, streamListener)
    }

	internal class InputStreamWrapper(
		private val request: WebResourceRequest,
		private val streamListener: StreamInterruptionListener
	) : InputStream() {
		private var inputStream: InputStream? = null
		private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

		override fun read(): Int {
			return if(inputStream == null) {
				val job = ioScope.async {
					streamListener.checkResourceAvailability(request).getOrNull()
				}

				runBlocking {
					inputStream = job.await()?.data
				}
				inputStream?.read()
			}
			else{
				inputStream?.read()
			} ?: 0
		}
	}

}
