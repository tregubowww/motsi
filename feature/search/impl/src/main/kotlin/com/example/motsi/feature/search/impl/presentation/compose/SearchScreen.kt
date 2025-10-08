package com.example.motsi.feature.search.impl.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    hideSplashScreen: () -> Unit,
    bottomNavBar: @Composable () -> Unit,
    filterData: SearchDestination.SearchFilterData
) {
    val screenState by viewModel.screenState.collectAsState()
    LifecycleEffect(onCreate = { viewModel.initViewModel() })
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

    val loadingStateSuccess = listActivityState.loadingState as? LoadingState.Success
    val searchQuery = loadingStateSuccess?.data?.searchQuery.orEmpty()
    val searchHint = loadingStateSuccess?.data?.searchHint ?: model.defaultSearchHint
    val historyTipList = loadingStateSuccess?.data?.historyTipList ?: persistentListOf()

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
        bottomBar = bottomNavBar
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ListSportActivity(viewModel)
        }
    }

}

@Composable
private fun ListSportActivity(viewModel: SearchViewModel) {
    val listActivityState by viewModel.listActivityState.collectAsState()
    val navController = LocalAppNavController.current
    when (val state = listActivityState.loadingState) {
        is LoadingState.Loading -> {
//            Loading() шимиризация
        }

        is LoadingState.Success -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp)
                    .clickable(onClick = {
                        viewModel.onListActivityIntent(
                            SearchListActivityIntent.ClickActivity(navController)
                        )
                    }),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize(), text = " детальная инфа по активности "
                )
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





