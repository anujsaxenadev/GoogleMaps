package com.wordpress.anujsaxenadev.googlemaps.features.map.states

sealed interface MapState {
    data object Loading: MapState
    data object CacheCleared: MapState
}