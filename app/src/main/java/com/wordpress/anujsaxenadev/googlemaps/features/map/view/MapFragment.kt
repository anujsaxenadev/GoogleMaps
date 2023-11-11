package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.wordpress.anujsaxenadev.googlemaps.core.ApplicationConstants
import com.wordpress.anujsaxenadev.googlemaps.core.applyMapSettings
import com.wordpress.anujsaxenadev.googlemaps.databinding.FragmentMapBinding
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment: Fragment(){
    private var fragmentMapBinding: FragmentMapBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)
        return fragmentMapBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateMap()
    }

    private fun inflateMap(){
        fragmentMapBinding?.mapView?.apply {
            webViewClient = mainViewModel.getWebViewClient()
            settings.applyMapSettings()
            loadUrl(ApplicationConstants.MAP_RESOURCES_REFERENCE)
        }
    }

}
