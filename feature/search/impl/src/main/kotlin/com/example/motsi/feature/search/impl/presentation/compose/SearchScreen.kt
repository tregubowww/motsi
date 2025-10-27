package com.example.motsi.feature.search.impl.presentation.compose

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
        val bottomPadding = padding.calculateBottomPadding()
        Box(
            modifier = Modifier
                .background(Tokens.Background.getColor())
                .padding(bottom = bottomPadding)
                .fillMaxSize()
        ) {
            ListActivity(
                viewModel = viewModel,
                screenModel = model,
                snackbarHostState = snackbarHostState
            )
        }
    }

}

@SuppressLint("RestrictedApi")
@Composable
private fun ListActivity(
    viewModel: SearchViewModel,
    snackbarHostState: SnackbarHostState,
    screenModel: SearchScreenModel
) {
    val listSportActivityState by viewModel.listActivityState.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val configuration = LocalConfiguration.current
    val navController = LocalAppNavController.current
    val density = LocalDensity.current
    val windowHeight =
        remember(mapState.isMapOpen, configuration) { configuration.screenHeightDp.dp - 120.dp }
    val initialListHeight = 400.dp
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemScrollOffset = with(density) { initialListHeight.toPx().toInt() }
    )
    val isAtTop by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 &&
                    scrollState.firstVisibleItemScrollOffset == 0
        }
    }
    val scrollProgress by remember {
        derivedStateOf {
            val currentScrollPx = scrollState.firstVisibleItemScrollOffset
            if (scrollState.firstVisibleItemIndex > 0) 1f
            //Делю текущий скролл на высоту экрана
            else (currentScrollPx.toFloat() / with(density) { windowHeight.toPx().toInt() })
        }
    }

    LaunchedEffect(scrollProgress) {
        viewModel.onMapIntent(SearchMapIntent.UpdateAlpha(scrollProgress))
    }

    // Автоматически открываем карту при скролле вниз cписка
    LaunchedEffect(isAtTop) {
        if (isAtTop  && !mapState.isMapOpen) {
            viewModel.onMapIntent(SearchMapIntent.ShowMap)
        }
    }

    // Скроллим к середине списка при нажатии на кнопку
    LaunchedEffect(mapState.isMapOpen) {
        if (mapState.isMapOpen) {
            scrollState.animateScrollToItem(
                index = 0,
                scrollOffset = with(density) { windowHeight.toPx().toInt() / 2 }
            )
        }
    }

    when (val _listActivityState = listSportActivityState.loadingState) {
        is LoadingState.Loading -> {
//            Loading() шимиризация
        }

        is LoadingState.Success -> {

            MapWidget(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                screenModel = screenModel,
            )

            if (!mapState.isMapOpen) {
                Box(modifier = Modifier.padding(top = 100.dp)) {

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onMapIntent(SearchMapIntent.ShowMap)
                                    }
                                    .background(color = Color.Transparent)
                                    .fillMaxWidth()
                                    .height(windowHeight)
                            )
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = Tokens.Background.getColor(),
                                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                                    )
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))

                                _listActivityState.data.sportActivityList.forEach { item ->
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
                    }
                }
            }
        }

        is LoadingState.Error -> {
//            Error() загрузка из кэша
        }

        else -> {
//            nothing
        }
    }
}