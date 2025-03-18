package com.example.questcityproject.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {

    // Default position (Saint Petersburg)
    private val defaultCenter = GeoPoint(59.9343, 30.3351)
    private val defaultZoom = 12.0

    // Map state
    private val _mapCenter = MutableLiveData<GeoPoint>().apply {
        value = defaultCenter
    }
    val mapCenter: LiveData<GeoPoint> = _mapCenter

    private val _zoomLevel = MutableLiveData<Double>().apply {
        value = defaultZoom
    }
    val zoomLevel: LiveData<Double> = _zoomLevel

    // Flag to track if map has been initialized
    private var _isMapInitialized = false
    val isMapInitialized get() = _isMapInitialized

    // Save map state
    fun saveMapState(center: GeoPoint, zoom: Double) {
        _mapCenter.value = center
        _zoomLevel.value = zoom
        _isMapInitialized = true
    }

    // Reset map state to default
    fun resetMapState() {
        _mapCenter.value = defaultCenter
        _zoomLevel.value = defaultZoom
        _isMapInitialized = false
    }
}