package com.example.motsi.feature.search.impl.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.viewModelScope
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.network.presentation.NetworkHelper
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.map.DataSnackbar
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchSportActivityListState
import com.example.motsi.feature.search.impl.models.presentation.map.MapState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.motsi.core.common.presentation.utils.LocationHelper

internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor,
    private val locationHelper: LocationHelper,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    /** Состояние загрузки экрана */
    val screenState: StateFlow<SearchScreenState> get() = _screenState.asStateFlow()
    private val _screenState =
        MutableStateFlow(SearchScreenState(loadingState = LoadingState.Loading))

    /** Состояние загрузки экрана */
    val listActivityState: StateFlow<SearchSportActivityListState> get() = _listActivityState.asStateFlow()
    private val _listActivityState =
        MutableStateFlow(SearchSportActivityListState(loadingState = LoadingState.Loading))

    /** Состояние карты */
    val mapState: StateFlow<MapState> get() = _mapState.asStateFlow()
    private val _mapState = MutableStateFlow(MapState())

    private val delayLoading = 500L
    private val zoomUserCameraPosition = 17f

    fun initViewModel() {
        viewModelScope.launch {
            launch {
                _screenState.value =
                    SearchScreenState(loadingState = interactor.getSearchScreen().handleState())
            }
            launch {
                _listActivityState.value =
                    SearchSportActivityListState(
                        loadingState = interactor.getSportActivityList().handleState()
                    )
            }
        }
    }

    fun onScreenIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.ClickSearchField -> {
                intent.navController.navigate(
                    SearchTipsDestination(
                        entryData = SearchTipsDestination.EntryData(
                            searchQuery = intent.searchQuery,
                            searchHint = intent.searchHint,
                            historyTipList = intent.historyTipList
                        )
                    )
                )
            }
        }
    }

    fun onMapIntent(intent: SearchMapIntent) {
        when (intent) {
            is SearchMapIntent.ShowSnackbar -> {
                _mapState.update {
                    it.copy(snackbarData = intent.dataSnackBar)
                }
            }

            is SearchMapIntent.OnLocationClick -> {
                takeUserLocation(intent.context)
            }

            is SearchMapIntent.UpdateCameraPosition -> {
                _mapState.update {
                    it.copy(
                        cameraPositionPoint = intent.cameraPosition.target,
                        cameraPositionZoom = intent.cameraPosition.zoom,
                        cameraPositionAzimuth = intent.cameraPosition.azimuth,
                        cameraPositionTilt = intent.cameraPosition.tilt
                    )
                }
            }

            is SearchMapIntent.ShowMap -> {
                _mapState.update { it.copy(isMapOpen = true) }
            }

            is SearchMapIntent.HideMap -> {
                _mapState.update { it.copy(isMapOpen = false) }
            }

            is SearchMapIntent.OnClickSnackbarAction -> {
                openSnackbarActionSettings(intent.context, intent.result)
            }

            is SearchMapIntent.UpdateAlpha -> {
                _mapState.update { it.copy(alpha = intent.alpha) }
            }

            is SearchMapIntent.UpdateZoom -> {
                _mapState.update { it.copy(cameraPositionZoom = intent.zoom) }
            }
        }
    }

    fun onListActivityIntent(intent: SearchListActivityIntent) {
        when (intent) {
            is SearchListActivityIntent.ClickSportActivity -> {
                intent.navController.navigate(
                    SportActivityDetailsGraph(id = intent.activityId)
                )
            }

            is SearchListActivityIntent.AddFilter -> {
                viewModelScope.launch {
                    _listActivityState.value =
                        SearchSportActivityListState(
                            loadingState = interactor.getSportActivityList(filterData = intent.filterData)
                                .handleState()
                        )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun takeUserLocation(context: Context) {
        //Проверка наличия разрешений геолокации
        if (!locationHelper.hasLocationPermission(context)) {
            _mapState.update { it.copy(isLocationLoading = false) }
            onMapIntent(
                SearchMapIntent.ShowSnackbar(
                    DataSnackbar(
                        message = (listActivityState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarPermission?.message.orEmpty(),
                        duration = SnackbarDuration.Short,
                        actionLabel = null
                    ), context
                )
            )
            return
        }

        //Проверка Интернета
        if (!networkHelper.isInternetAvailable(context)) {
            _mapState.update { it.copy(isLocationLoading = false) }
            onMapIntent(
                SearchMapIntent.ShowSnackbar(
                    DataSnackbar(
                        message = (listActivityState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarInternet?.message.orEmpty(),
                        duration = SnackbarDuration.Short,
                        actionLabel = null
                    ), context
                )
            )
            return
        }

        //Проверка влюченности геолокации
        if (!locationHelper.isLocationEnabled(context)) {
            _mapState.update { it.copy(isLocationLoading = false) }
            onMapIntent(
                SearchMapIntent.ShowSnackbar(
                    DataSnackbar(
                        message = (listActivityState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarLocation?.message.orEmpty(),
                        duration = SnackbarDuration.Short,
                        actionLabel = (listActivityState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarLocation?.actionLabel.orEmpty(),
                        type = DataSnackbar.SnackbarType.ActionMessage
                    ), context
                )
            )
            return
        }

        _mapState.update { it.copy(isLocationLoading = true)}
        //Показ снекбара о ожидании геолокации
        viewModelScope.launch(Dispatchers.IO) {
            val snackbarJob = launch {
                delay(delayLoading) //Задержка
                onMapIntent(
                    SearchMapIntent.ShowSnackbar(
                        DataSnackbar(
                            message = (listActivityState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarLoadingLocation?.message.orEmpty(),
                            duration = SnackbarDuration.Short,
                            actionLabel = null
                        ), context
                    )
                )
            }

            locationHelper.getCurrentLocation(
                onSuccess = { location ->
                    snackbarJob.cancel()

                    _mapState.update {
                        it.copy(
//                            cameraPositionZoom = zoomUserCameraPosition,
//                            cameraPositionPoint = Point(location.latitude, location.longitude), //Перемещаем камеру на локацию пользователя
                            isLocationLoading = false, //Убираем индикатор поиска локации
                            userLocationPlacemark = Point(location.latitude, location.longitude), //Обновляем позицию пользователя
                            snackbarData = null
                        )
                    }
                },
                onFailure = { /** Обработать ошибку? */ },
                context
            )
        }
    }

    private fun openSnackbarActionSettings(context: Context, result: SnackbarResult?) {
        when (result) {
            SnackbarResult.ActionPerformed -> {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                try {
                    context.startActivity(intent)
                } catch (_: Exception) {
                }
            }

            SnackbarResult.Dismissed -> {}
            null -> {}
        }
    }

    override fun onRelease() {
        SearchHolder.release()
    }
}