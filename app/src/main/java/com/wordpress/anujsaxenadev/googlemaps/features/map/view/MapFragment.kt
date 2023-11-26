package com.wordpress.anujsaxenadev.googlemaps.features.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.wordpress.anujsaxenadev.googlemaps.core.ApplicationConstants
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.applyMapSettings
import com.wordpress.anujsaxenadev.googlemaps.core.extensions.launchWhenStarted
import com.wordpress.anujsaxenadev.googlemaps.databinding.FragmentMapBinding
import com.wordpress.anujsaxenadev.googlemaps.features.map.states.MapState
import com.wordpress.anujsaxenadev.googlemaps.features.map.view_model.MapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment: Fragment(){
    private var fragmentMapBinding: FragmentMapBinding? = null
    private val mapViewModel by activityViewModels<MapViewModel>()

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
        mapViewModel.clearCache(this)
        setObservers()
    }

    private fun setObservers() {
        launchWhenStarted(lifecycleScope){
            mapViewModel.cacheClearingState.collect{
                when(it){
                    MapState.CacheCleared -> {
                        toggleMapView(true)
                        inflateMap()
                    }
                    MapState.Loading -> {
                        toggleMapView(false)
                    }
                }
            }
        }
    }

    private fun toggleMapView(enableMap: Boolean){
        if(enableMap){
            fragmentMapBinding?.mapView?.visibility = View.VISIBLE
            fragmentMapBinding?.loader?.visibility = View.GONE
        }
        else{
            fragmentMapBinding?.loader?.visibility = View.VISIBLE
            fragmentMapBinding?.mapView?.visibility = View.GONE
        }
    }

    private fun inflateMap(){
        fragmentMapBinding?.mapView?.apply {
            webViewClient = mapViewModel.getWebViewClient()
            settings.applyMapSettings()
            loadUrl(ApplicationConstants.MAP_RESOURCES_REFERENCE)
        }
    }
}