package com.example.questcityproject.ui.map
import android.util.Log
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.questcityproject.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var zoomInButton: Button
    private lateinit var zoomOutButton: Button
    private lateinit var geoButton: ImageButton
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var viewModel: MapViewModel

    // List to store marker data
    private val markerDataList = mutableListOf<MarkerData>()

    // Zoom threshold for showing radius overlays
    private val RADIUS_ZOOM_THRESHOLD = 17.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize OSMDroid
        Configuration.getInstance().userAgentValue = requireContext().packageName

        // Load layout
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Initialize MapView
        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Initialize buttons

        geoButton = view.findViewById(R.id.geoButton)

        // Set up button click listeners


        // Initialize location overlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mapView)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()

        val imageDraw = ContextCompat.getDrawable(requireContext(),R.drawable.ic_my_location)!!.toBitmap()
        myLocationOverlay.setPersonIcon(imageDraw)
        myLocationOverlay.setDirectionIcon(imageDraw)

        geoButton.setOnClickListener {
            myLocationOverlay.myLocation?.let {
                mapView.controller.animateTo(it)
                saveMapState()
            }
        }

        // Update position when location changes
        myLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                myLocationOverlay.myLocation?.let {
                    if (!viewModel.isMapInitialized) {
                        mapView.controller.animateTo(it)
                        saveMapState()
                    }
                }
            }
        }

        // Add overlay to map
        mapView.overlays.add(myLocationOverlay)

        // Request location permissions
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        // Add markers
        addAllMarkers()

        // Restore map state from ViewModel
        restoreMapState()

        // Update radius visibility based on current zoom
        updateRadiusVisibility()

        // Set up map movement listener to save state and update radius visibility
        mapView.addMapListener(object : org.osmdroid.events.MapListener {
            override fun onScroll(event: org.osmdroid.events.ScrollEvent?): Boolean {
                saveMapState()
                return true
            }

            override fun onZoom(event: org.osmdroid.events.ZoomEvent?): Boolean {
                saveMapState()
                updateRadiusVisibility()
                return true
            }
        })

        return view
    }

    private fun updateRadiusVisibility() {
        val currentZoom = mapView.zoomLevelDouble
        val showRadius = currentZoom >= RADIUS_ZOOM_THRESHOLD

        // Update visibility of all radius overlays
        for (markerData in markerDataList) {
            markerData.radiusOverlay.setVisible(showRadius)
        }

        // Force redraw
        mapView.invalidate()
    }

    private fun restoreMapState() {
        viewModel.mapCenter.value?.let { center ->
            viewModel.zoomLevel.value?.let { zoom ->
                mapView.controller.setZoom(zoom)
                mapView.controller.setCenter(center)
            }
        }
    }

    private fun saveMapState() {
        val center = mapView.mapCenter as GeoPoint
        val zoom = mapView.zoomLevelDouble
        viewModel.saveMapState(center, zoom)
    }

    private fun addAllMarkers() {
        // Clear existing markers
        markerDataList.clear()

        // Add all your markers with their corresponding radius drawables
        addMarkerWithRadius(GeoPoint(59.973023, 30.324240), "ЛЭТИ", R.drawable.ic_map_red, R.drawable.radius_red)
        addMarkerWithRadius(GeoPoint(59.990980, 30.318177), "Общежитие 8", R.drawable.ic_map_yellow, R.drawable.radius_yellow)
        addMarkerWithRadius(GeoPoint(60.003682, 30.287553), "Общежитие 7", R.drawable.ic_map_yellow, R.drawable.radius_yellow)
        addMarkerWithRadius(GeoPoint(59.969605, 30.299366), "Общежитие 6", R.drawable.ic_map_yellow, R.drawable.radius_yellow)
        addMarkerWithRadius(GeoPoint(59.877962, 30.242889), "Общежитие 11", R.drawable.ic_map_yellow, R.drawable.radius_yellow)
        addMarkerWithRadius(GeoPoint(59.869720, 30.309265), "МСГ", R.drawable.ic_map_yellow, R.drawable.radius_yellow)
        addMarkerWithRadius(GeoPoint(59.956435, 30.308726), "ИТМО", R.drawable.ic_map_green, R.drawable.radius_green)
        addMarkerWithRadius(GeoPoint(59.925903, 30.319857), "Яблоки", R.drawable.ic_map_blue, R.drawable.radius_blue)
        addMarkerWithRadius(GeoPoint(59.938861, 30.365868), "Груши", R.drawable.ic_map_blue, R.drawable.radius_blue)
        addMarkerWithRadius(GeoPoint(59.958047, 30.337359), "Сампсониевский", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.949084, 30.285442), "Тучков", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.946166, 30.303431), "Биржевой", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.934892, 30.289371), "Благовещенский", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.941326, 30.307736), "Дворцовый", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.948637, 30.327564), "Троицкий", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.952143, 30.349531), "Литейный", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.942731, 30.401357), "Большеохтинский", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.926039, 30.396617), "Александра Невского", R.drawable.ic_map_violet, R.drawable.radius_violet)
        addMarkerWithRadius(GeoPoint(59.877711, 30.453146), "Володарский", R.drawable.ic_map_violet, R.drawable.radius_violet)
    }

    private fun addMarkerWithRadius(position: GeoPoint, title: String, iconResId: Int, radiusDrawableId: Int) {
        // Create marker
        val marker = Marker(mapView)
        marker.position = position
        marker.title = title
        val icon = ContextCompat.getDrawable(requireContext(), iconResId)
        if (icon != null) {
            marker.icon = icon
        }
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Create radius overlay
        val radiusOverlay = RadiusOverlay(requireContext(), position, radiusDrawableId)

        // Add marker and radius to map
        mapView.overlays.add(radiusOverlay)
        mapView.overlays.add(marker)

        // Store marker data for later reference
        markerDataList.add(MarkerData(marker, radiusOverlay))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        myLocationOverlay.enableMyLocation()
        updateRadiusVisibility()
    }

    override fun onPause() {
        super.onPause()
        saveMapState() // Save state when pausing
        mapView.onPause()
        myLocationOverlay.disableMyLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDetach()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            myLocationOverlay.enableMyLocation()
        }
    }

    // Data class to store marker and its associated radius overlay
    data class MarkerData(val marker: Marker, val radiusOverlay: RadiusOverlay)
}