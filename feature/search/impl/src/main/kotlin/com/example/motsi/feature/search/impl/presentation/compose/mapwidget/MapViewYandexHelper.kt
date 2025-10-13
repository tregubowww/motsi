package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import android.content.Context
import com.example.motsi.core.ui.utils.convertSportActivityIconToBitmap
import com.example.motsi.core.ui.utils.createUserLocationBitmap
import com.example.motsi.core.ui.utils.toIconRes
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

fun MapObjectCollection.updateUserLocationPlacemark(
    userLocationPlacemark: Point?,
    mapView: MapView
) {
    this.clear() //Отчистка старой метки
    userLocationPlacemark?.let { itemUserPlacemark ->
        this.addPlacemark().apply {
            geometry = itemUserPlacemark
            setIcon(
                ImageProvider.fromBitmap(
                    createUserLocationBitmap()
                )
            )
            zIndex = 100f
        }
        mapView.mapWindow.map.move(
            CameraPosition(userLocationPlacemark, 17f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 0.5f),
            null
        )
    }
}

fun MapObjectCollection.updateActivityMarkers(
    listActivityState: SearchSportActivityListModel,
    context: Context
) {
    this.clear()
    listActivityState.sportActivityList.forEach { markerData ->
        this.addPlacemark().apply {
            geometry = Point(
                markerData.mapData.locationPoint.first,
                markerData.mapData.locationPoint.second
            )
            setIcon(
                ImageProvider.fromBitmap(
                    convertSportActivityIconToBitmap(
                        context = context,
                        iconActivity = markerData.mapData.iconMark.toIconRes()
                    )
                )
            )
        }
    }
}