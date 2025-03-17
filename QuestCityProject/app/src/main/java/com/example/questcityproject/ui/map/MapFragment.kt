package com.example.questcityproject.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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

class MapFragment : Fragment() {

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

        addMarker(GeoPoint(59.973023, 30.324240),"ЛЭТИ",R.drawable.ic_map_red)
        addMarker(GeoPoint(59.990980, 30.318177),"Общежитие 8",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.987299, 30.330672),"Общежитие 1",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.987299, 30.330672),"Общежитие 2",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.987299, 30.330672),"Общежитие 3",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.985620, 30.331319),"Общежитие 4",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(60.003682, 30.287553),"Общежитие 7",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.969605, 30.299366),"Общежитие 6",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.877962, 30.242889),"Общежитие 11",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.869720, 30.309265),"МСГ",R.drawable.ic_map_yellow)
        addMarker(GeoPoint(59.956435, 30.308726),"ИТМО",R.drawable.ic_map_green)
        addMarker(GeoPoint(59.925903, 30.319857),"Яблоки",R.drawable.ic_map_blue)
        addMarker(GeoPoint(59.938861, 30.365868),"Груши",R.drawable.ic_map_blue)
        addMarker(GeoPoint(59.978588, 30.321836),"Кантемировский",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.968102, 30.334937),"Гренадерский",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.958047, 30.337359),"Сампсониевский",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.949084, 30.285442),"Тучков",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.946166, 30.303431),"Биржевой",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.934892, 30.289371),"Благовещенский",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.941326, 30.307736),"Дворцовый",R.drawable.ic_map_violet)
        addMarker(GeoPoint( 59.948637, 30.327564),"Троицкий",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.952143, 30.349531),"Литейный",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.942731, 30.401357),"Большеохтинский",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.926039, 30.396617),"Александра Невского",R.drawable.ic_map_violet)
        addMarker(GeoPoint(59.877711, 30.453146),"Володарский",R.drawable.ic_map_violet)












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


        // Обновление позиции маркера при изменении местоположения
        myLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                mapView.controller.animateTo(myLocationOverlay.myLocation)
            }
        }

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