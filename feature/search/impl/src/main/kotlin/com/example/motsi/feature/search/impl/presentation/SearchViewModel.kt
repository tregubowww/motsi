package com.example.motsi.feature.search.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.enumOrDefault
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.ui.designsystem.mapwidget.BaseMapWidgetViewModel
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidgetState
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchFilterModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchIntent
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenEffect
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor,
    locationHelper: LocationHelperWrapper,
    networkHelper: NetworkHelperWrapper,
    resourceManager: ResourceManager,
    mapboxWrapper: MapboxWrapper,
) : BaseMapWidgetViewModel<SearchIntent>(
    locationHelper,
    networkHelper,
    resourceManager,
    mapboxWrapper
) {

    /** Состояние экрана */
    private val screenReducer = UiReducer(SearchScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<SearchScreenState> get() = screenReducer.state

    /** Состояние списка */
    private val listReducer =
        UiReducer(SearchListActivityState(loadingState = LoadingState.Loading))
    val listActivityState: StateFlow<SearchListActivityState> get() = listReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<SearchScreenEffect>()
    val effect: SharedFlow<SearchScreenEffect> get() = effectHandler.effect

//    private var selectedSportActivityMarker: MapWidgetState.Marker? = null

    /** Состояние списка */
    private val selectedSportActivityMarkerReducer =
        UiReducer<SearchSportActivityListModel.MapData.Marker?>(null)
    val selectedSportActivityMarker: StateFlow<SearchSportActivityListModel.MapData.Marker?> get() = selectedSportActivityMarkerReducer.state

    init {
        runCatching { mapboxWrapper.initialize() }
            .onFailure { Log.e("SearchViewModel", "Mapbox init failed", it) }

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

    override fun onGeoPointChanged(
        latitude: Double,
        longitude: Double,
        zoom: Double,
        rotation: Float
    ) {
        getSportActivityList(
            SearchFilterModel(
                SearchFilterModel.MapFilter(
                    latitude,
                    longitude,
                    zoom
                )
            )
        )
    }

    /** Screen Intents */
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

            is SearchScreenIntent.ChangeScreenStateToMapAndList -> {
                screenReducer.update { copy(screenState = SearchScreenState.ScreenState.MAP_AND_LIST) }
            }

            is SearchScreenIntent.ChangeScreenStateToList -> {
                screenReducer.update { copy(screenState = SearchScreenState.ScreenState.LIST) }
            }

            is SearchScreenIntent.ChangeScreenStateToMap -> {
                screenReducer.update { copy(screenState = SearchScreenState.ScreenState.MAP) }
            }
        }
    }

    /** Map Intents */
    private fun handleMapIntent(intent: SearchMapIntent) {
        when (intent) {
            is SearchMapIntent.OnGetMobileLocationClick -> takeMobileLocation()
            is SearchMapIntent.OnPointClick -> {
                val markers =
                    (listActivityState.value.loadingState as? LoadingState.Success)?.data?.mapData?.markers
                selectedSportActivityMarkerReducer.update { markers?.find { it.id == intent.id } }
                markers?.toMapMarkers()?.let { updateMarkers(it) }
                screenReducer.update { copy(screenState = SearchScreenState.ScreenState.MAP_AND_LIST) }

                getSportActivityList(
                    SearchFilterModel(
                        SearchFilterModel.MapFilter(
                            choiceMarker = intent.id
                        )
                    )
                )
            }

            is SearchMapIntent.OnShowMobileGeoPosition -> onShowMobileGeoPosition()
            is SearchMapIntent.OnCameraMoved -> {
                cameraMoved(
                    intent.latitude,
                    intent.longitude,
                    intent.zoom,
                    intent.rotation,
                )
            }

            is SearchMapIntent.UpdateAlpha -> changeAlpha(intent.alpha)
        }
    }

    private fun getSportActivityList(searchFilterModel: SearchFilterModel) {
        viewModelScope.launch {
            val listResult =
                interactor.getSportActivityList(searchFilterModel)
                    .handleState(eventOnSuccess = { list ->
                        updateMarkers(list.mapData.markers.toMapMarkers())
                    })

            listReducer.update { copy(loadingState = listResult) }
        }
    }

    private fun List<SearchSportActivityListModel.MapData.Marker>.toMapMarkers(): List<MapWidgetState.Marker> {
        return map {
            val isSelected = it.id == selectedSportActivityMarker.value?.id
            MapWidgetState.Marker(
                id = it.id,
                latitude = it.locationPoint.first,
                longitude = it.locationPoint.second,
                description = it.description,
                type = if (isSelected) {
                    MapWidgetState.Marker.Type.SelectedMarker
                } else {
                    it.type.enumOrDefault(MapWidgetState.Marker.Type.Dot)
                }
            )
        }
    }

    /**  List Intents */
    private fun handleListIntent(intent: SearchListActivityIntent) {
        when (intent) {
            is SearchListActivityIntent.ClickSportActivity -> SearchScreenEffect.NavigateToActivityDetails(
                intent.activityId
            ).emit()

            is SearchListActivityIntent.AddFilter -> viewModelScope.launch {
                TODO("фильтрация списка активностей")
            }

            is SearchListActivityIntent.ClickLikeSportActivity -> viewModelScope.launch {
                val currentState = listReducer.state.value
                val successState = currentState.loadingState as? LoadingState.Success
                    ?: return@launch

                listReducer.update {
                    copy(
                        loadingState = successState.copy(
                            data = successState.data.copy(
                                sportActivityList = successState.data.sportActivityList.map {
                                    if (it.id == intent.activityId) {
                                        it.copy(isLiked = !it.isLiked)
                                    } else it
                                }.toImmutableList()
                            )
                        )
                    )
                }
//                // TODO: Отправить запрос на сервер для обновления лайка
            }

            is SearchListActivityIntent.ClickAddSportActivity -> viewModelScope.launch {
                val currentState = listReducer.state.value
                val successState = currentState.loadingState as? LoadingState.Success
                    ?: return@launch

                listReducer.update {
                    copy(
                        loadingState = successState.copy(
                            data = successState.data.copy(
                                sportActivityList = successState.data.sportActivityList.map {
                                    if (it.id == intent.activityId) {
                                        it.copy(isAdd = !it.isAdd)
                                    } else it
                                }.toImmutableList()
                            )
                        )
                    )
                }
//                // TODO: Отправить запрос на сервер для обновления состояния
            }
        }
    }

    private fun SearchScreenEffect.emit() = viewModelScope.launch { effectHandler.emit(this@emit) }

    private fun loadInitialData() {
        viewModelScope.launch {
            val screenDeferred = async { interactor.getSearchScreen() }
            val listDeferred =
                async { interactor.getSportActivityList() }

            val screenResult = screenDeferred.await().handleState()

            val listResult = listDeferred.await().handleState(eventOnSuccess = { list ->
                setNewGeoPoint(
                    latitude = list.cityLocation.cityPoint.first,
                    longitude = list.cityLocation.cityPoint.second,
                    zoom = list.cityLocation.cityZoom,
                    rotation = 0f
                )
                updateMarkers(list.mapData.markers.toMapMarkers())
            })


            screenReducer.update { copy(loadingState = screenResult) }
            listReducer.update { copy(loadingState = listResult) }
        }
    }

    override fun onRelease() {
        SearchHolder.release()
    }
}