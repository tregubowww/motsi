package com.example.motsi.feature.search.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.TipsField
import com.example.motsi.core.ui.theming.SubtitleBlue
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.search.impl.presentation.SearchClickHandler
import com.example.motsi.feature.search.impl.presentation.SearchTipsViewModel

@Composable
internal fun SearchTipsScreen(
    viewModel: SearchTipsViewModel,
    clickHandler: SearchClickHandler,
    bottomNavBar: @Composable () -> Unit
) {
//    val tipsListState by viewModel.searchTipsScreenState.collectAsState()

//    when (val state = tipsListState.loadingState) {
//        is LoadingState.Loading -> {
////            Loading() шимиризация
//        }
//
//        is LoadingState.Success -> {
    val listTipsState by viewModel.listTipsState.collectAsState()
    val navController = LocalAppNavController.current
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Tokens.BackgroundFont.getColor()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchAppBar(
                    modifier = Modifier.weight(4f),
                    onTextChange = { text -> viewModel.onSearchQueryChange(text) },
                    textSearch = searchQuery,
                    hint = navController.currentBackStackEntry?.arguments?.getString("appBarHint") ?: "Поиск",
                    onKeyboardSearchButtonClick = { text ->
                        viewModel.getSearchQuery(text)
                        clickHandler.onTipClick(navController)
                    },
                    isEnabled = true
                )

                SubtitleBlue(
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(end = 14.dp)
                        .clickable(onClick = { clickHandler.onClickCancel(navController) })
                        .windowInsetsPadding(WindowInsets.statusBars),
                    text = "Отменить" //Где хранить string res этого модуля?
                )
            }
        },
        bottomBar = bottomNavBar
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = Tokens.BackgroundFont.getColor())
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .imePadding()
            ) {
                items(minOf(listTipsState.tipsList.size, 8)) { index ->
                    val tipItem = listTipsState.tipsList[index]
                    TipsField(
                        onTipsFieldClicked = {
                            clickHandler.onTipClick(navController)
                            viewModel.onSearchQueryChange(tipItem.tips)
                            viewModel.getSearchQuery(tipItem.tips)
                        },
                        iconTips = if (tipItem.isInSearchHistory) {
                            com.example.motsi.core.ui.R.drawable.ic_clock_history_20dp
                        } else {
                            com.example.motsi.core.ui.R.drawable.ic_search_20dp
                        },
                        tips = tipItem.tips,
                        tipsCategory = tipItem.tipsCategory,
                        isNotLastItem = index < minOf(listTipsState.tipsList.size - 1, 7)
                    )
                }
            }
        }
    }
}

//        is LoadingState.Error -> {
//            Text(
//                modifier = Modifier
//                    .fillMaxSize(), text = state.error.message
//            )
////            Error()
//        }
//
//        else -> {
////            nothing
//        }
//    }
//}