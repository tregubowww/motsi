package com.example.motsi.feature.search.impl.presentation.compose

import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListActivity(
    viewModel: SearchViewModel,
    snackbarHostState: SnackbarHostState,
    screenModel: SearchScreenModel
) {
    val listSportActivityState by viewModel.listActivityState.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val navController = LocalAppNavController.current
    val scrollState = rememberLazyListState()
    val density = LocalDensity.current
    val initialLitSize = 350.dp
    // Состояние для BottomSheet
    val sheetState =
        rememberStandardBottomSheetState(skipHiddenState = false) // Разрешаем скрытое состояние
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    // Проверка, можно ли опускать BottomSheet
    val canCollapseSheet =
        remember(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset) {
            scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset == 0
        }
    // Проверка, можно ли скроллить список
    val canScrollList = sheetState.currentValue == SheetValue.Expanded

    // Автоматический показ карты, когда BottomSheet полностью скрывается
    LaunchedEffect(sheetState.currentValue) {
        if (!mapState.isMapOpen && sheetState.currentValue == SheetValue.Hidden) {
            viewModel.onMapIntent(SearchMapIntent.ShowMap)
        }
    }
    // Автоматический показ списка на середину при закрытии карты
    LaunchedEffect(mapState.isMapOpen) {
        if (!mapState.isMapOpen && sheetState.currentValue == SheetValue.Hidden) {
            sheetState.show()
        }
    }

    // Рассчет альфы на основе позиции BottomSheet
    val scrollProgress by remember {
        derivedStateOf {
            try {
                val offset = sheetState.requireOffset() // текущее смещение в px
                val initialLitSizePx = with(density) { initialLitSize.toPx() }
                val progress = (offset - initialLitSizePx) / (0f - initialLitSizePx)
                progress.coerceIn(0f, 1f)
            } catch (e: IllegalStateException) {
                0f // если offset еще не инициализирован
            }
        }
    }

    LaunchedEffect(scrollProgress) {
        viewModel.onMapIntent(SearchMapIntent.UpdateAlpha(scrollProgress))
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
                Column(modifier = Modifier.fillMaxSize()) {

                    Spacer(modifier = Modifier.height(100.dp))

                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            LazyColumn(
                                state = scrollState,
                                modifier = Modifier.fillMaxSize(),
                                userScrollEnabled = canScrollList
                            ) {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .background(
                                                color = Tokens.Background.getColor(),
                                                shape = RoundedCornerShape(
                                                    topStart = 20.dp,
                                                    topEnd = 20.dp
                                                )
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
                        },
                        sheetShadowElevation = 12.dp,
                        sheetPeekHeight = initialLitSize,
                        sheetDragHandle = { },
                        sheetSwipeEnabled = canCollapseSheet
                    ) { }
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
