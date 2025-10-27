package com.example.motsi.feature.search.impl.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityState
import com.example.motsi.feature.search.impl.models.presentation.map.MapState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@OptIn(FlowPreview::class)
internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor,
    private val mapboxWrapper: MapboxWrapper,
    private val locationHelper: LocationHelperWrapper,
    private val networkHelper: NetworkHelperWrapper
) : BaseViewModel() {

    /** Состояние экрана */
    private val _screenState =
        MutableStateFlow(SearchScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<SearchScreenState> get() = _screenState.asStateFlow()

    /** Состояние списка активностей */
    private val _listActivityState =
        MutableStateFlow(SearchListActivityState(loadingState = LoadingState.Loading))
    val listActivityState: StateFlow<SearchListActivityState> get() = _listActivityState.asStateFlow()

    /** Состояние карты */
    private val _mapState = MutableStateFlow(MapState())
    val mapState: StateFlow<MapState> get() = _mapState.asStateFlow()

    /** Поток изменений GeoPoint */
    private val _geoPointFlow = MutableSharedFlow<SearchMapIntent.ChangeGeoPoint>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val delayLoading = 500L

    init {
        mapboxWrapper.initialize()
        observeGeoPointChanges()
        loadInitialData()
    }

    /** Helper для обновления состояния карты */
    private fun updateMapState(update: MapState.() -> MapState) {
        _mapState.update { it.update() }
    }

    /** Загрузка данных экрана и списка активностей параллельно */
    private fun loadInitialData() {
        viewModelScope.launch {
            val screenDeferred = async { interactor.getSearchScreen() }
            val listDeferred = async { interactor.getSportActivityList() }

            _screenState.value = SearchScreenState(
                loadingState = screenDeferred.await().handleState()
            )

            _listActivityState.value = SearchListActivityState(
                loadingState = listDeferred.await().handleState(eventOnSuccess = { list ->
                    updateMapState {
                        copy(
                            currentGeoPoint = GeoPoint(
                                list.cityLocation.cityPoint.first,
                                list.cityLocation.cityPoint.second
                            ),
                            currentZoom = list.cityLocation.cityZoom
                        )
                    }
                })
            )
        }
    }

    /** Обработка интентов экрана */
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

    /** Обработка интентов карты */
    fun onMapIntent(intent: SearchMapIntent) {
        when (intent) {
            is SearchMapIntent.ShowSnackbar -> updateMapState { copy(dataSnackbar = dataSnackbar) }
            is SearchMapIntent.OnLocationClick -> takeUserLocation()
            is SearchMapIntent.OnShowUserGeoposition -> updateMapState { copy(moveToUserGeoPosition = false) }
            is SearchMapIntent.ChangeGeoPoint -> _geoPointFlow.tryEmit(intent)
            is SearchMapIntent.ShowMap -> updateMapState { copy(isMapOpen = true) }
            is SearchMapIntent.HideMap -> updateMapState { copy(isMapOpen = false) }
            is SearchMapIntent.UpdateAlpha -> updateMapState { copy(alpha = intent.alpha) }
        }
    }

    /** Обработка интентов списка активностей */
    fun onListActivityIntent(intent: SearchListActivityIntent) {
        when (intent) {
            is SearchListActivityIntent.ClickSportActivity -> intent.navController.navigate(
                SportActivityDetailsGraph(id = intent.activityId)
            )

            is SearchListActivityIntent.AddFilter -> {
                viewModelScope.launch {
                    _listActivityState.value = SearchListActivityState(
                        loadingState = interactor.getSportActivityList(filterData = intent.filterData)
                            .handleState()
                    )
                }
            }
        }
    }

    /** Отслеживание изменений GeoPoint с дебаунсом */
    private fun observeGeoPointChanges(debounceMillis: Long = 300L) {
        _geoPointFlow
            .debounce(debounceMillis)
            .onEach { handleGeoPointChange(it) }
            .launchIn(viewModelScope)
    }

    /** Обработка изменения геопозиции */
    private fun handleGeoPointChange(intent: SearchMapIntent.ChangeGeoPoint) {
        if (GeoPoint(
                intent.latitude,
                intent.longitude
            ) != mapState.value.currentGeoPoint || intent.zoom != mapState.value.currentZoom
        ) {
            updateMapState {
                copy(
                    currentGeoPoint = GeoPoint(intent.latitude, intent.longitude),
                    currentZoom = intent.zoom,
                    currentRotation = intent.rotation
                )
            }

            // TODO: здесь можно вызывать backend
        }
    }

    /** Получение геопозиции пользователя с безопасными проверками */
    @SuppressLint("MissingPermission")
    private fun takeUserLocation() {
        val dataSnackbarText =
            (screenState.value.loadingState as? LoadingState.Success)?.data?.dataSnackbarText

        fun String.show() =
            updateMapState { copy(dataSnackbar = DataSnackbar(message = this@show)) }

        when {
            !locationHelper.hasLocationPermission() -> {
                updateMapState { copy(isLocationLoading = false) }
                updateMapState { copy(dataSnackbar = dataSnackbar) }
                dataSnackbarText?.dataSnackbarPermission?.message.orEmpty().show()
                return
            }

            !networkHelper.isInternetAvailable() -> {
                updateMapState { copy(isLocationLoading = false) }
                dataSnackbarText?.dataSnackbarInternet?.message.orEmpty().show()
                return
            }

            !locationHelper.isLocationEnabled() -> {
                updateMapState { copy(isLocationLoading = false) }
                updateMapState {
                    copy(
                        dataSnackbar = DataSnackbar(
                            message = dataSnackbarText?.dataSnackbarLocation?.message.orEmpty(),
                            actionLabel = dataSnackbarText?.dataSnackbarLocation?.actionLabel.orEmpty(),
                            type = DataSnackbar.SnackbarType.Action
                        )
                    )
                }
                return
            }
        }

        updateMapState { copy(isLocationLoading = true) }

        viewModelScope.launch {
            val snackbarJob = launch {
                delay(delayLoading)
                dataSnackbarText?.dataSnackbarLoadingLocation?.message.orEmpty().show()
            }

            val location = try {
                locationHelper.getCurrentLocationOrNull()
            } catch (_: Exception) {
                null
            }

            snackbarJob.cancel()

            if (location != null) {
                updateMapState {
                    copy(
                        isLocationLoading = false,
                        userGeoPosition = GeoPoint(location.latitude, location.longitude),
                        dataSnackbar = null,
                        moveToUserGeoPosition = true
                    )
                }
            } else {
                updateMapState { copy(isLocationLoading = false) }
            }
        }
    }

    override fun onRelease() {
        SearchHolder.release()
    }
}