package com.example.motsi.feature.search.impl.presentation.compose

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.designsystem.buttons.IconTextButton
import com.example.motsi.core.ui.designsystem.fields.ItemSportActivity
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidget
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidgetActions
import com.example.motsi.core.ui.designsystem.snackbar.CustomSnackbarHost
import com.example.motsi.core.ui.designsystem.snackbar.showMotsiSnackbar
import com.example.motsi.core.ui.models.ItemSportActivityButton
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.SearchIntent
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenEffect
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState.ScreenState
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    hideSplashScreen: () -> Unit,
    bottomNavBar: @Composable () -> Unit,
    filterData: SearchDestination.SearchFilterData
) {
    val screenState by viewModel.screenState.collectAsState()
    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            hideSplashScreen.invoke()
            SearchScreenSuccess(
                model = state.data,
                viewModel = viewModel,
                bottomNavBar = bottomNavBar,
            )
        }

        is LoadingState.Error -> {
//            Error()
        }

        else -> {
//            nothing
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenSuccess(
    model: SearchScreenModel,
    viewModel: SearchViewModel,
    bottomNavBar: @Composable () -> Unit,
) {
    val navController = LocalAppNavController.current
    val listActivityState by viewModel.listActivityState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val loadingStateSuccess = listActivityState.loadingState as? LoadingState.Success
    val searchQuery = loadingStateSuccess?.data?.searchQuery.orEmpty()
    val searchHint = loadingStateSuccess?.data?.searchHint ?: model.defaultSearchHint
    val historyTipList = loadingStateSuccess?.data?.historyTipList ?: persistentListOf()
    val context = LocalContext.current
    val sheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val coroutineScope = rememberCoroutineScope()
    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchAppBar(
                onSearchFieldClick = {
                    viewModel.dispatch(
                        SearchIntent.Screen(
                            SearchScreenIntent.ClickSearchField(
                                searchQuery = searchQuery,
                                searchHint = searchHint,
                                historyTipList = historyTipList,
                            )
                        )
                    )

                },
                backgroundColorSearchField = if (screenState.screenState == SearchScreenState.ScreenState.LIST) Tokens.BackgroundSecondary.getColor() else Tokens.Background.getColor(),
                hint = searchHint,
                textSearch = searchQuery,
                onTextChange = {},
                isEnabled = false
            )
        },
        bottomBar = bottomNavBar,
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackbarHostState,
            )
        }
    ) { padding ->
        val mapState by viewModel.mapState.collectAsState()
        Box(
            modifier = Modifier
                .background(Tokens.Background.getColor())
                .fillMaxSize()
        ) {
            MapWidget(
                actions = MapWidgetActions(
                    onMapClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                    onCameraMoved = { lat, lon, zoom, rotation ->
                        viewModel.dispatch(
                            SearchIntent.Map(
                                SearchMapIntent.OnCameraMoved(
                                    lat,
                                    lon,
                                    zoom,
                                    rotation
                                )
                            )
                        )
                    },
                    onShowMobileLocation = {
                        viewModel.dispatch(
                            SearchIntent.Map(
                                SearchMapIntent.OnShowMobileGeoPosition
                            )
                        )
                    },

                    onGetMobileLocationClick = {
                        viewModel.dispatch(
                            SearchIntent.Map(
                                SearchMapIntent.OnGetMobileLocationClick(context)
                            )
                        )
                    },
                    onPointClick = { id ->
                        viewModel.dispatch(
                            SearchIntent.Map(
                                SearchMapIntent.OnPointClick(id)
                            )
                        )
                    },
                ),
                snackBarFlow = viewModel.snackBar,
                snackbarHostState = snackbarHostState,
                state = mapState,
            )

            ListSportActivity(
                viewModel = viewModel,
                padding = padding,
                screenModel = model,
                sheetState = sheetState
            )

            CollectEffect(viewModel.effect) { effect ->
                when (effect) {
                    is SearchScreenEffect.NavigateToSearchTips -> {
                        navController.navigate(SearchTipsDestination(effect.entryData))
                    }

                    is SearchScreenEffect.ShowSnackbar -> {
                        coroutineScope.launch {
                            snackbarHostState.showMotsiSnackbar(
                                effect.dataSnackbar,
                                onActionPerformed = {
                                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                }
                            )
                        }
                    }

                    is SearchScreenEffect.NavigateToActivityDetails -> {
                        navController.navigate(SportActivityDetailsGraph(effect.activityId))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListSportActivity(
    viewModel: SearchViewModel,
    padding: PaddingValues,
    screenModel: SearchScreenModel,
    sheetState: SheetState
) {
    val listSportActivityState by viewModel.listActivityState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val halfScreen = LocalConfiguration.current.screenHeightDp.dp / 2
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val canCollapseSheet by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 &&
                    scrollState.firstVisibleItemScrollOffset == 0
        }
    }
    val density = LocalDensity.current


    val alphaProgress by produceState(0f, sheetState) {
        snapshotFlow { sheetState.requireOffset() }
            .map { offset ->
                val totalHeight = with(density) { halfScreen.toPx() }
                val progress = ((totalHeight - offset) / totalHeight).coerceIn(0f, 1f)
                if (progress < 0.5f) 0f
                else ((progress - 0.5f) / 0.5f).coerceIn(0f, 1f)
            }
            .collect { value = it }
    }

    LaunchedEffect(sheetState.currentValue) {
        when (sheetState.currentValue) {
            SheetValue.Hidden -> {
                viewModel.dispatch(SearchIntent.Screen(SearchScreenIntent.ChangeScreenStateToMap))
            }

            SheetValue.PartiallyExpanded -> {
                viewModel.dispatch(SearchIntent.Screen(SearchScreenIntent.ChangeScreenStateToMapAndList))
            }

            SheetValue.Expanded -> {
                viewModel.dispatch(SearchIntent.Screen(SearchScreenIntent.ChangeScreenStateToList))
            }
        }
    }

    LaunchedEffect(screenState.screenState) {
        if (screenState.screenState == ScreenState.MAP_AND_LIST && sheetState.currentValue != SheetValue.PartiallyExpanded) {
            sheetState.partialExpand()
        }
    }

    LaunchedEffect(alphaProgress) {
        viewModel.dispatch(SearchIntent.Map(SearchMapIntent.UpdateAlpha(alphaProgress)))
    }

    when (val listActivityState = listSportActivityState.loadingState) {
        is LoadingState.Loading -> {
//            Loading() шимиризация
        }

        is LoadingState.Success -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetShadowElevation = (alphaProgress / 12).dp,
                    sheetPeekHeight = halfScreen,
                    sheetDragHandle = { },
                    sheetSwipeEnabled = canCollapseSheet,

                    sheetContent = {
                        LazyColumn(
                            state = scrollState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Tokens.Background.getColor()),
                            userScrollEnabled = screenState.screenState == ScreenState.LIST,
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(
                                items = listActivityState.data.sportActivityList,
                                key = { item -> item.id }
                            ) { item ->
                                ItemSportActivity(
                                    urlPicsList = item.participantList.map { it.urlUserPic },
                                    title = item.title,
                                    onClickItem = {
                                        viewModel.dispatch(
                                            SearchIntent.List(
                                                SearchListActivityIntent.ClickSportActivity(
                                                    activityId = item.id
                                                )
                                            )
                                        )
                                    },
                                    subtitle = item.subtitle,
                                    subtitleIcon = ItemSportActivityButton(
                                        icon = AppResources.icon(item.descriptionActivityIcon),
                                        tint = Tokens.IconPrimary.getColor(),
                                        onClick = {
                                            TODO("клик на кнопке приватности")
                                        }
                                    ),
                                    description = item.description,
                                    logo = ItemSportActivityButton(
                                        icon = AppResources.icon(item.logoIcon),
                                        tint = AppResources.color(item.logoColor),
                                        onClick = {}
                                    ),
                                    rightButtons = persistentListOf(

                                        ItemSportActivityButton(
                                            icon = if (item.isLiked) {
                                                painterResource(R.drawable.ic_like_fill_24dp)
                                            } else {
                                                painterResource(R.drawable.ic_like_24dp)
                                            },
                                            tint = if (item.isLiked) {
                                                Tokens.IconFavorites.getColor()
                                            } else {
                                                Tokens.IconPrimary.getColor()
                                            },
                                            onClick = {
                                                viewModel.dispatch(
                                                    SearchIntent.List(
                                                        SearchListActivityIntent.ClickLikeSportActivity(
                                                            activityId = item.id
                                                        )
                                                    )
                                                )
                                            }
                                        ),

                                        ItemSportActivityButton(
                                            icon = if (item.isAdd) {
                                                painterResource(R.drawable.ic_circleplus_fill_24dp)
                                            } else {
                                                painterResource(R.drawable.ic_circleplus_24dp)
                                            },
                                            tint = if (item.isLiked) {
                                                Tokens.IconBrand1.getColor()
                                            } else {
                                                Tokens.IconBrand1.getColor()
                                            },
                                            onClick = {
                                                viewModel.dispatch(
                                                    SearchIntent.List(
                                                        SearchListActivityIntent.ClickAddSportActivity(
                                                            activityId = item.id
                                                        )
                                                    )
                                                )
                                            }
                                        )
                                    )
                                )
                            }
                        }
                    }
                ) {
                }
                when (screenState.screenState) {
                    SearchScreenState.ScreenState.LIST -> {
                        IconTextButton(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomStart),
                            text = screenModel.buttonTextForMapOpen,
                            icon = R.drawable.ic_map_geopoint_outline_24dp,
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                            }
                        )
                    }

                    SearchScreenState.ScreenState.MAP -> {
                        IconTextButton(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomStart),
                            text = screenModel.buttonTextForListOpen,
                            icon = R.drawable.ic_view_list_24dp,
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.expand()
                                }
                            }
                        )
                    }

                    else -> Unit
                }
            }
        }

        is LoadingState.Error -> {
//            Error() загрузка из кэша
        }

        else -> Unit
    }
}