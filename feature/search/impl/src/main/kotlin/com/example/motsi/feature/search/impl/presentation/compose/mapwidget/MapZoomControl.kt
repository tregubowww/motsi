package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

//Работало так, но изменилось отображение карты. Возможно, нужно удалить
fun MapView.zoomPlus(animationDuration: Float = 0.3f) {
    val map = this.mapWindow.map
    val currentZoom = map.cameraPosition.zoom
    val newZoom = (currentZoom + 1f).coerceIn(0f, 20f)

    map.move(
        CameraPosition(
            map.cameraPosition.target,
            newZoom,
            map.cameraPosition.azimuth,
            map.cameraPosition.tilt
        ),
        Animation(Animation.Type.SMOOTH, animationDuration),
        null
    )
}

fun MapView.zoomMinus(animationDuration: Float = 0.3f) {
    val map = this.mapWindow.map
    val currentZoom = map.cameraPosition.zoom
    val newZoom = (currentZoom - 1f).coerceIn(0f, 20f)

    map.move(
        CameraPosition(
            map.cameraPosition.target,
            newZoom,
            map.cameraPosition.azimuth,
            map.cameraPosition.tilt
        ),
        Animation(Animation.Type.SMOOTH, animationDuration),
        null
    )
}