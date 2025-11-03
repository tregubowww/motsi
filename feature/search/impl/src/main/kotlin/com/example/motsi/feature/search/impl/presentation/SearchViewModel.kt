package com.example.motsi.feature.search.impl.presentation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.presentation.SearchIntent
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityState
import com.example.motsi.feature.search.impl.models.presentation.map.MapState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenEffect
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@OptIn(FlowPreview::class)
internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor,
    private val locationHelper: LocationHelperWrapper,
    private val networkHelper: NetworkHelperWrapper,
    mapboxWrapper: MapboxWrapper,
) : BaseViewModel<SearchIntent>() {

    /** Состояние экрана */
    private val screenReducer = UiReducer(SearchScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<SearchScreenState> get() = screenReducer.state

    /** Состояние списка */
    private val listReducer = UiReducer(SearchListActivityState(loadingState = LoadingState.Loading))
    val listActivityState: StateFlow<SearchListActivityState> get() = listReducer.state

    /** Состояние карты */
    private val mapReducer = UiReducer(MapState())
    val mapState: StateFlow<MapState> get() = mapReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<SearchScreenEffect>()
    val effect: SharedFlow<SearchScreenEffect> get() = effectHandler.effect

    private val _geoPointFlow = MutableSharedFlow<SearchMapIntent.ChangeGeoPoint>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val delayLoading = 500L

    init {
        runCatching { mapboxWrapper.initialize() }
            .onFailure { Log.e("SearchViewModel", "Mapbox init failed", it) }

        observeGeoPointChanges()
        loadInitialData()
    }

    /** Универсальная функция отправки Intent */
    override fun dispatch(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Screen -> handleScreenIntent(intent.value)
            is SearchIntent.Map -> handleMapIntent(intent.value)
            is SearchIntent.List -> handleListIntent(intent.value)
        }
    }

    /** Screen Intents */
    @OptIn(ExperimentalMaterial3Api::class)
    private fun handleScreenIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.ClickSearchField -> {
                SearchScreenEffect.NavigateToSearchTips(
                    entryData = SearchTipsDestination.EntryData(
                        searchQuery = intent.searchQuery,
                        searchHint = intent.searchHint,
                        historyTipList = intent.historyTipList
                    )
                ).emit()
            }

            is SearchScreenIntent.ChangeScreenState -> {
                val newState = when (intent.currentValue) {
                    SheetValue.Hidden -> SearchScreenState.ScreenState.MAP
                    SheetValue.PartiallyExpanded -> SearchScreenState.ScreenState.MAP_AND_LIST
                    SheetValue.Expanded -> SearchScreenState.ScreenState.LIST
                }
                screenReducer.update { copy(screenState = newState) }
            }
        }
    }

    /** Map Intents */
    private fun handleMapIntent(intent: SearchMapIntent) {
        when (intent) {
            is SearchMapIntent.OnLocationClick -> takeUserLocation()
            is SearchMapIntent.OnShowUserGeoposition -> mapReducer.update { copy(moveToUserGeoPosition = false) }
            is SearchMapIntent.ChangeGeoPoint -> _geoPointFlow.tryEmit(intent)
            is SearchMapIntent.UpdateAlpha -> mapReducer.update { copy(alpha = intent.alpha) }
        }
    }

    /**  List Intents */
    private fun handleListIntent(intent: SearchListActivityIntent) {
        when (intent) {
            is SearchListActivityIntent.ClickSportActivity -> SearchScreenEffect.NavigateToActivityDetails(intent.activityId).emit()
            is SearchListActivityIntent.AddFilter -> viewModelScope.launch {
//                //поход на бэк
            }
        }
    }

    private fun SearchScreenEffect.emit() = viewModelScope.launch { effectHandler.emit(this@emit) }
    private fun observeGeoPointChanges(debounceMillis: Long = 300L) {
        _geoPointFlow.debounce(debounceMillis)
            .onEach { handleGeoPointChange(it) }
            .launchIn(viewModelScope)
    }

    private fun handleGeoPointChange(intent: SearchMapIntent.ChangeGeoPoint) {
        val current = mapReducer.current() // Чтение через reducer
        val newPoint = GeoPoint(intent.latitude, intent.longitude)

        if (current.currentGeoPoint != newPoint || current.currentZoom != intent.zoom || current.currentRotation != intent.rotation) {
            mapReducer.update { // Обновление через reducer
                copy(
                    currentGeoPoint = GeoPoint(intent.latitude, intent.longitude),
                    currentZoom = intent.zoom,
                    currentRotation = intent.rotation
                )
            }
        }
    }

    private fun takeUserLocation() {
        val dataSnackbar = (screenReducer.current().loadingState as? LoadingState.Success)?.data?.dataSnackbarText // Чтение через reducer

        when {
            !locationHelper.hasLocationPermission() -> {
                mapReducer.update { copy(isLocationLoading = false) }
                showSnackbar(dataSnackbar?.dataSnackbarPermission?.message.orEmpty())
                return
            }
            !networkHelper.isInternetAvailable() -> {
                mapReducer.update { copy(isLocationLoading = false) }
                showSnackbar(dataSnackbar?.dataSnackbarInternet?.message.orEmpty())
                return
            }
            !locationHelper.isLocationEnabled() -> {
                mapReducer.update { copy(isLocationLoading = false) }
                showSnackbar(
                    message = dataSnackbar?.dataSnackbarLocation?.message.orEmpty(),
                    actionLabel = dataSnackbar?.dataSnackbarLocation?.actionLabel.orEmpty(),
                    type = DataSnackbar.SnackbarType.Action
                )
                return
            }
        }

        mapReducer.update { copy(isLocationLoading = true) }

        viewModelScope.launch {
            val snackbarJob = launch {
                delay(delayLoading)
                dataSnackbar?.dataSnackbarLoadingLocation?.message?.let { msg ->
                    showSnackbar(msg)
                }
            }

            val location = try {
                kotlinx.coroutines.withTimeoutOrNull(10_000L) {
                    locationHelper.getCurrentLocationOrNull()
                }
            } catch (t: Throwable) {
                Log.e("SearchViewModel", "Failed to get location", t)
                null
            }

            snackbarJob.cancel()

            if (location != null) {
                mapReducer.update {
                    copy(
                        isLocationLoading = false,
                        userGeoPosition = GeoPoint(location.latitude, location.longitude),
                        moveToUserGeoPosition = true
                    )
                }
            } else {
                mapReducer.update { copy(isLocationLoading = false) }
                showSnackbar(dataSnackbar?.dataSnackbarLocation?.message.orEmpty())
            }
        }
    }

    private fun showSnackbar(message: String, actionLabel: String = "", type: DataSnackbar.SnackbarType = DataSnackbar.SnackbarType.Default) {
        if (message.isEmpty()) return
        SearchScreenEffect.ShowSnackbar(
            DataSnackbar(
                message = message,
                actionLabel = actionLabel,
                type = type
            )
        ).emit()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val screenDeferred = async { interactor.getSearchScreen() }
            val listDeferred = async { interactor.getSportActivityList() }

            val screenResult = screenDeferred.await().handleState()
            val listResult = listDeferred.await().handleState(eventOnSuccess = { list ->
                mapReducer.update {
                    copy(
                        currentGeoPoint = GeoPoint(
                            list.cityLocation.cityPoint.first,
                            list.cityLocation.cityPoint.second
                        ),
                        currentZoom = list.cityLocation.cityZoom
                    )
                }
            })

            screenReducer.update { copy(loadingState = screenResult) }
            listReducer.update { copy(loadingState = listResult) }
        }
    }

    override fun onRelease() { SearchHolder.release() }
}