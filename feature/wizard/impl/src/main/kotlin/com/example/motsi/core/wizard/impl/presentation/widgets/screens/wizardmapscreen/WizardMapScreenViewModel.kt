package com.example.motsi.core.wizard.impl.presentation.widgets.screens.wizardmapscreen

import com.example.motsi.core.ui.designsystem.mapwidget.BaseMapWidgetViewModel
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidgetState.Marker
import com.example.motsi.core.wizard.impl.interactor.WizardInteractor
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class WizardMapScreenViewModel @AssistedInject constructor(
    locationHelper: LocationHelperWrapper,
    networkHelper: NetworkHelperWrapper,
    resourceManager: ResourceManager,
    private val interactor: WizardInteractor,
    mapboxWrapper: MapboxWrapper,
    @Assisted val mapScreenModel: WizardCoordinatorModel.MapScreenModel,
) : BaseMapWidgetViewModel<WizardMapScreenIntent>(
    locationHelper,
    networkHelper,
    resourceManager,
    mapboxWrapper
) {


    init {
        setNewGeoPoint(
            latitude = mapScreenModel.markers.cityLocation.cityPoint.first,
            longitude = mapScreenModel.markers.cityLocation.cityPoint.second,
            zoom = mapScreenModel.markers.cityLocation.cityZoom,
            rotation = 0f
        )
        updateMarkers(mapScreenModel.markers.pointList.map {
            Marker(
                id = it.id,
                latitude = it.mapData.locationPoint.first,
                longitude = it.mapData.locationPoint.second,
                type = Marker.Type.Dot,
                description = ""
//                поменять на данные из интерактора

            )
        })
    }

    /** Универсальная функция отправки Intent */
    override fun dispatch(intent: WizardMapScreenIntent) {
        when (intent) {
            is WizardMapScreenIntent.OnLocationClick -> takeMobileLocation()
            is WizardMapScreenIntent.OnShowUserGeoposition -> onShowMobileGeoPosition()
            is WizardMapScreenIntent.CameraMoved -> {
//                interactor.getMarkersForMapScreen(urlGetMarkers = mapScreenModel.urlGetMarkers, )
                cameraMoved(
                    intent.latitude,
                    intent.longitude,
                    intent.zoom,
                    intent.rotation
                )
            }
        }
    }

    override fun onGeoPointChanged(
        latitude: Double,
        longitude: Double,
        zoom: Double,
        rotation: Float
    ) {
//                interactor.getMarkersForMapScreen(urlGetMarkers = mapScreenModel.urlGetMarkers, )
    }

    override fun onRelease() = Unit

    @AssistedFactory
    interface Factory {
        fun create(mapScreenModel: WizardCoordinatorModel.MapScreenModel): WizardMapScreenViewModel
    }
}


