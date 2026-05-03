package com.example.motsi.core.ui.designsystem.mapwidget

import android.content.Context

data class MapWidgetActions(
    val onMapClick: () -> Unit = {},
    val onPointClick: (String) -> Unit = {},
    val onCameraMoved: (Double, Double, Double, Float) -> Unit = { _, _, _, _ -> },
    val onRequestLocation: (Context) -> Unit = {},
    val onShowMobileLocation: () -> Unit = {},
    val onGetMobileLocationClick: (Context) -> Unit = {},
)