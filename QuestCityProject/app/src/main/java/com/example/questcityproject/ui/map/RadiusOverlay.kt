package com.example.questcityproject.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay

class RadiusOverlay(
    private val context: Context,
    private val geoPoint: GeoPoint,
    private val radiusDrawableId: Int
) : Overlay() {

    private val radiusDrawable: Drawable = ContextCompat.getDrawable(context, radiusDrawableId)!!
    private var isVisible = false

    // Base size at zoom level 17
    private val BASE_SIZE = 80
    private val BASE_ZOOM = 17.0

    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow || !isVisible) return

        // Convert GeoPoint to screen coordinates
        val point = Point()
        mapView.projection.toPixels(geoPoint, point)

        // Calculate size based on zoom level to maintain consistent visual size
        val currentZoom = mapView.zoomLevelDouble
        val zoomFactor = Math.pow(2.0, currentZoom - BASE_ZOOM)
        val size = (BASE_SIZE * zoomFactor).toInt()

        // Set bounds for the drawable
        radiusDrawable.setBounds(
            point.x - size / 2,
            point.y - size / 2,
            point.x + size / 2,
            point.y + size / 2
        )

        // Draw the radius
        radiusDrawable.draw(canvas)
    }

    fun setVisible(visible: Boolean) {
        isVisible = visible
    }
}