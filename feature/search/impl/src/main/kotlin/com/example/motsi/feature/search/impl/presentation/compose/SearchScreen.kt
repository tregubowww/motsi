package com.example.motsi.feature.search.impl.presentation.compose

import android.content.Intent
import android.provider.Settings
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.designsystem.buttons.IconTextButton
import com.example.motsi.core.ui.designsystem.snackbar.CustomSnackbarHost
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.SearchIntent
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenEffect
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import com.example.motsi.feature.search.impl.presentation.compose.mapwidget.MapWidget
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
    val lifecycleOwner = LocalLifecycleOwner.current

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
                onAction = {
                    try {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    } catch (e: Exception) {
                        Log.e("SearchScreen", "Cannot open location settings", e)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(Tokens.Background.getColor())
                .fillMaxSize()
        ) {
            MapWidget(
                viewModel = viewModel,
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                showWidgetFullScreen = { coroutineScope.launch { sheetState.hide() } }
            )

            ListActivity(
                viewModel = viewModel,
                padding = padding,
                screenModel = model,
                sheetState = sheetState
            )


            LaunchedEffect(lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.effect.collect { effect ->
                        when (effect) {
                            is SearchScreenEffect.NavigateToSearchTips -> {
                                navController.navigate(SearchTipsDestination(effect.entryData))
                            }

                            is SearchScreenEffect.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(effect.dataSnackbar)
                            }

                            is SearchScreenEffect.NavigateToActivityDetails -> {
                                navController.navigate(SportActivityDetailsGraph(effect.activityId))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListActivity(
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
        viewModel.dispatch(SearchIntent.Screen(SearchScreenIntent.ChangeScreenState(sheetState.currentValue)))
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
                            userScrollEnabled = screenState.screenState == SearchScreenState.ScreenState.LIST,
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(
                                items = listActivityState.data.sportActivityList,
                                key = { item -> item.id }
                            ) { item ->
                                ItemSportActivity(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 16.dp),
                                    sportActivityItem = item,
                                    onClick = {
                                        viewModel.dispatch(
                                            SearchIntent.List(
                                                SearchListActivityIntent.ClickSportActivity(
                                                    activityId = item.id
                                                )
                                            )
                                        )
                                    }
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