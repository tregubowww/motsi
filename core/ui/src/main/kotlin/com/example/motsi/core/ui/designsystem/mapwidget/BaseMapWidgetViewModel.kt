package com.example.motsi.core.ui.designsystem.mapwidget

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.domain.SnackbarType
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidgetState.Marker
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.osmdroid.util.GeoPoint


@OptIn(FlowPreview::class)
abstract class BaseMapWidgetViewModel<T>(
    private val locationHelper: LocationHelperWrapper,
    private val networkHelper: NetworkHelperWrapper,
    private val resourceManager: ResourceManager,
    mapboxWrapper: MapboxWrapper,
) : BaseViewModel<T>() {

    /** Состояние карты */
    private val mapReducer = UiReducer(MapWidgetState())
    val mapState: StateFlow<MapWidgetState> get() = mapReducer.state

    /** Эффекты */
    private val snackBarHandler = EffectHandler<DataSnackbar>()
    val snackBar: SharedFlow<DataSnackbar> get() = snackBarHandler.effect

    private val _geoPointFlow = MutableSharedFlow<MapWidgetGeoPoint>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        runCatching { mapboxWrapper.initialize() }
            .onFailure { Log.e("MapWidgetViewModel", "Mapbox init failed", it) }
        observeGeoPointChanges()
    }

    private fun observeGeoPointChanges(debounceMillis: Long = 300L) {
        _geoPointFlow.debounce(debounceMillis)
            .onEach { handleGeoPointChange(it) }
            .launchIn(viewModelScope)
    }

    open fun takeMobileLocation(
        mapWidgetDataSnackBar: MapWidgetDataSnackbar? = null,
        delayLoading: Long = 500L
    ) {
        when {
            !locationHelper.isLocationEnabled() -> {
                mapReducer.update { copy(isMobileLocationLoading = false) }
                showSnackbar(
                    message = mapWidgetDataSnackBar?.dataSnackbarLocation?.message
                        ?: resourceManager.getString(R.string.dataSnackBarLocation),
                    actionLabel = mapWidgetDataSnackBar?.dataSnackbarLocation?.actionLabel
                        ?: resourceManager.getString(R.string.dataSnackBarLocationActionLabel),
                    type = SnackbarType.Action
                )
                return
            }
        }

        mapReducer.update { copy(isMobileLocationLoading = true) }

        viewModelScope.launch {
            val snackbarJob = launch {
                delay(delayLoading)
                showSnackbar(
                    mapWidgetDataSnackBar?.dataSnackbarLoadingLocation?.message
                        ?: resourceManager.getString(R.string.dataSnackbarLoadingLocation)
                )
            }

            val location = try {
                withTimeoutOrNull(10_000L) {
                    locationHelper.getCurrentLocationOrNull()
                }
            } catch (t: Throwable) {
                null
            }

            snackbarJob.cancel()

            if (location != null) {
                mapReducer.update {
                    copy(
                        isMobileLocationLoading = false,
                        mobileGeoPosition = GeoPoint(location.latitude, location.longitude),
                        moveToMobileGeoPosition = true
                    )
                }
            } else {
                mapReducer.update { copy(isMobileLocationLoading = false) }
                showSnackbar(
                    mapWidgetDataSnackBar?.dataSnackbarLocation?.message
                        ?: resourceManager.getString(R.string.dataSnackBarLocation)
                )
            }
        }
    }

    open fun onShowMobileGeoPosition() {
        mapReducer.update {
            copy(
                moveToMobileGeoPosition = false
            )
        }
    }

    open fun cameraMoved(
        latitude: Double,
        longitude: Double,
        zoom: Double,
        rotation: Float,
    ) {
        _geoPointFlow.tryEmit(
            MapWidgetGeoPoint(
                latitude,
                longitude,
                zoom,
                rotation
            )
        )
    }

    open fun setNewGeoPoint(
        latitude: Double,
        longitude: Double,
        zoom: Double,
        rotation: Float
    ) {
        if (mapState.value.currentGeoPoint?.latitude != latitude || mapState.value.currentGeoPoint?.longitude != longitude || mapState.value.currentZoom != zoom || mapState.value.currentRotation != rotation) {
            mapReducer.update {
                copy(
                    currentGeoPoint = GeoPoint(latitude, longitude),
                    currentZoom = zoom,
                    currentRotation = rotation
                )
            }
        }
    }

    open fun changeAlpha(alpha: Float) {
        mapReducer.update {
            copy(
                alpha = alpha
            )
        }
    }

    open fun updateMarkers(markers: List<Marker>) {
        mapReducer.update {
            copy(
                markers = markers
            )
        }
    }

    protected open fun onGeoPointChanged(
        latitude: Double,
        longitude: Double,
        zoom: Double,
        rotation: Float
    ) = Unit

    private fun handleGeoPointChange(geoPoint: MapWidgetGeoPoint) {
        mapReducer.update {
            copy(
                currentGeoPoint = GeoPoint(geoPoint.latitude, geoPoint.longitude),
                currentZoom = geoPoint.zoom,
                currentRotation = geoPoint.rotation
            )
        }
        onGeoPointChanged(
            geoPoint.latitude,
            geoPoint.longitude,
            geoPoint.zoom,
            geoPoint.rotation
        )
    }

    private fun showSnackbar(
        message: String,
        actionLabel: String = "",
        type: SnackbarType = SnackbarType.Default
    ) {
        viewModelScope.launch {
            snackBarHandler.emit(
                DataSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    type = type
                )
            )
        }
    }
}

private data class MapWidgetGeoPoint(
    val latitude: Double,
    val longitude: Double,
    val zoom: Double,
    val rotation: Float
)