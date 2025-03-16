package com.example.questcityproject.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.questcityproject.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapElementFragment : Fragment() {

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инициализация OSMDroid
        org.osmdroid.config.Configuration.getInstance().userAgentValue = requireContext().packageName

        // Загрузка макета
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Инициализация MapView
        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK) // Используем стандартный источник карт
        mapView.setMultiTouchControls(true) // Включаем мультитач-управление

        // Установка начальной позиции карты (например, Санкт-Петербург)
        val startPoint = GeoPoint(59.9343, 30.3351) // Широта и долгота
        mapView.controller.setZoom(12.0) // Уровень масштабирования
        mapView.controller.setCenter(startPoint)

        // Добавление маркера
        val marker = Marker(mapView)
//        marker.position = startPoint
//        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        marker.title = "Санкт-Петербург"
//        mapView.overlays.add(marker)

        // Локация
        val locationOverlay = MyLocationNewOverlay(mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)

        // Инициализация кнопок
        val zoomInButton: Button = view.findViewById(R.id.zoomInButton)
        val zoomOutButton: Button = view.findViewById(R.id.zoomOutButton)

        // Обработка нажатий на кнопки
        zoomInButton.setOnClickListener {
            mapView.controller.zoomIn() // Увеличиваем масштаб
        }

        zoomOutButton.setOnClickListener {
            mapView.controller.zoomOut() // Уменьшаем масштаб
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume() // Обязательно вызывайте onResume для MapView
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause() // Обязательно вызывайте onPause для MapView
    }
}