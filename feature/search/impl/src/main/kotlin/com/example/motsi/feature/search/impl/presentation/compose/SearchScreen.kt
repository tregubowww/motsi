package com.example.motsi.feature.search.impl.presentation.compose

import android.app.Activity
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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.designsystem.snackbar.CustomSnackbarHost
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import com.example.motsi.feature.search.impl.presentation.compose.mapwidget.MapWidget
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.map

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
            Success(
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

@Composable
private fun Success(
    model: SearchScreenModel,
    viewModel: SearchViewModel,
    bottomNavBar: @Composable () -> Unit,
) {
    val navController = LocalAppNavController.current
    val listActivityState by viewModel.listActivityState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val mapState by viewModel.mapState.collectAsState()
    val loadingStateSuccess = listActivityState.loadingState as? LoadingState.Success
    val searchQuery = loadingStateSuccess?.data?.searchQuery.orEmpty()
    val searchHint = loadingStateSuccess?.data?.searchHint ?: model.defaultSearchHint
    val historyTipList = loadingStateSuccess?.data?.historyTipList ?: persistentListOf()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchAppBar(
                onSearchFieldClick = {
                    viewModel.onScreenIntent(
                        SearchScreenIntent.ClickSearchField(
                            navController = navController,
                            searchQuery = searchQuery,
                            searchHint = searchHint,
                            historyTipList = historyTipList,
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
                screenModel = model,
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                snackbarHostState = snackbarHostState
            )

            if (!mapState.isMapOpen) {
                ListActivity(
                    viewModel = viewModel,
                    padding = padding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListActivity(
    viewModel: SearchViewModel,
    padding: PaddingValues,
) {
    val listSportActivityState by viewModel.listActivityState.collectAsState()
    val navController = LocalAppNavController.current
    val scrollState = rememberLazyListState()
    val halfScreen = LocalConfiguration.current.screenHeightDp.dp / 2
    val sheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val canCollapseSheet by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 &&
                    scrollState.firstVisibleItemScrollOffset == 0
        }
    }
    val canScrollList by remember { derivedStateOf { sheetState.currentValue == SheetValue.Expanded } }
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
            SheetValue.Hidden -> viewModel.onMapIntent(SearchMapIntent.ShowMap)
            SheetValue.PartiallyExpanded, SheetValue.Expanded -> viewModel.onMapIntent(
                SearchMapIntent.HideMap
            )
        }
    }

    LaunchedEffect(alphaProgress) {
        viewModel.onMapIntent(SearchMapIntent.UpdateAlpha(alphaProgress))
    }

    when (val listActivityState = listSportActivityState.loadingState) {
        is LoadingState.Loading -> {
//            Loading() шимиризация
        }

        is LoadingState.Success -> {

            val view = LocalView.current

            SideEffect {
                val window = (view.context as Activity).window
                WindowCompat.setDecorFitsSystemWindows(window, false)
            }
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
                            userScrollEnabled = canScrollList,
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
                                        viewModel.onListActivityIntent(
                                            SearchListActivityIntent.ClickSportActivity(
                                                navController = navController,
                                                activityId = item.id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) {
                }
            }
        }

        is LoadingState.Error -> {
//            Error() загрузка из кэша
        }

        else -> Unit
    }
}
