package com.example.questcityproject.ui.mapElement

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.questcityproject.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapElementFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var zoomInButton: Button
    private lateinit var zoomOutButton: Button
    private lateinit var geoButton: Button
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var locationMarker: Marker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инициализация OSMDroid
        Configuration.getInstance().userAgentValue = requireContext().packageName

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
        marker.position = GeoPoint(59.973023, 30.324240)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "ЛЭТИ"
        mapView.overlays.add(marker)

        val marker1 = Marker(mapView)
        marker1.position = GeoPoint(59.956435, 30.308726)
        marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker1.title = "ИТМО"
        mapView.overlays.add(marker1)

        // Инициализация кнопок
        zoomInButton = view.findViewById(R.id.zoomInButton)
        zoomOutButton = view.findViewById(R.id.zoomOutButton)
//        geoButton = view.findViewById(R.id.geoButton)

        // Обработка нажатий на кнопки
        zoomInButton.setOnClickListener {
            mapView.controller.zoomIn() // Увеличиваем масштаб
        }

        zoomOutButton.setOnClickListener {
            mapView.controller.zoomOut() // Уменьшаем масштаб
        }

//        geoButton.setOnClickListener {
//            mapView.controller.animateTo(myLocationOverlay.myLocation)
//        }

        // Инициализация MyLocationNewOverlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mapView)
        myLocationOverlay.enableMyLocation() // Включаем отображение текущего местоположения
        

        // Добавляем наложение на карту
        mapView.overlays.add(myLocationOverlay)

        // Запрашиваем разрешения на доступ к геолокации
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

        return view
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume() // Обязательно вызывайте onResume для MapView
        myLocationOverlay.enableMyLocation() // Включаем отображение местоположения при возобновлении
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause() // Обязательно вызывайте onPause для MapView
        myLocationOverlay.disableMyLocation() // Отключаем отображение местоположения при паузе
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDetach() // Отсоединяем карту
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Разрешение предоставлено, включаем отображение местоположения
            myLocationOverlay.enableMyLocation()
        }
    }
}