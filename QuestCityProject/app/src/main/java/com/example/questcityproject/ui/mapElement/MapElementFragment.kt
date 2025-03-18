package com.example.questcityproject.ui.mapElement

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
    private lateinit var geoButton: ImageButton
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
        addMarker(GeoPoint(59.990980, 30.318177),"Общежитие 8",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.987299, 30.330672),"Общежития 1,2,3",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.985620, 30.331319),"Общежитие 4",R.drawable.ic_map_red)
        addMarker(GeoPoint(60.003682, 30.287553),"Общежитие 7",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.969605, 30.299366),"Общежитие 6",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.877962, 30.242889),"Общежитие 11",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.869720, 30.309265),"МСГ",R.drawable.ic_map_red)

        // Инициализация кнопок

        geoButton = view.findViewById(R.id.geoButton)



        geoButton.setOnClickListener {
            mapView.controller.animateTo(myLocationOverlay.myLocation)
        }

        // Инициализация MyLocationNewOverlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mapView)
        myLocationOverlay.enableMyLocation() // Включаем отображение текущего местоположения
        myLocationOverlay.enableFollowLocation()

        val imageDraw = ContextCompat.getDrawable(requireContext(),R.drawable.ic_my_location)!!.toBitmap()
        myLocationOverlay.setPersonIcon(imageDraw)
        myLocationOverlay.setDirectionIcon(imageDraw)
        

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


    // Метод для добавления маркера на карту
    private fun addMarker(position: GeoPoint, title: String, iconResId: Int) {
        // Создаем маркер
        val marker = Marker(mapView)

        // Устанавливаем позицию маркера
        marker.position = position

        // Устанавливаем заголовок маркера
        marker.title = title

        // Устанавливаем иконку маркера
        val icon = ContextCompat.getDrawable(requireContext(), iconResId)
        if (icon != null) {
            marker.icon = icon
        }

        // Настраиваем якорь (точку привязки иконки к координатам)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Добавляем маркер на карту
        mapView.overlays.add(marker)
    }
}