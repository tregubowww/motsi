package com.example.motsi.feature.search.impl.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.theming.Subtitle2Primary
import com.example.motsi.core.ui.theming.SubtitleBrand
import com.example.motsi.core.ui.theming.Title2Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.search.impl.presentation.SearchClickHandler
import com.example.motsi.feature.search.impl.presentation.SearchTipsViewModel

@Composable
internal fun SearchTipsScreen(
    viewModel: SearchTipsViewModel,
    clickHandler: SearchClickHandler,
    bottomNavBar: @Composable () -> Unit,
    searchQuery: String,
    appBarHint: String
) {
//    val tipsListState by viewModel.searchTipsScreenState.collectAsState()

//    when (val state = tipsListState.loadingState) {
//        is LoadingState.Loading -> {
////            Loading() шимиризация
//        }
//
//        is LoadingState.Success -> {
    LaunchedEffect(Unit) {
        viewModel.initSearchQuery(searchQuery)
    }
    val currentQuery by viewModel.searchQuery.collectAsState()
    val listTipsState by viewModel.listTipsState.collectAsState()
    val navController = LocalAppNavController.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Tokens.Background.getColor()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchAppBar(
                    modifier = Modifier.weight(4f),
                    onTextChange = { text -> viewModel.onSearchQueryChange(text) },
                    textSearch = currentQuery,
                    hint = appBarHint,
                    onKeyboardSearchButtonClick = { text ->
                        viewModel.getSearchTips(text)
                        clickHandler.onTipClick(navController, text)
                    },
                    isEnabled = true
                )

                SubtitleBrand(
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
                .background(color = Tokens.Background.getColor())
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .imePadding()
            ) {
                items(minOf(listTipsState.tipsList.size, 8)) { index ->
                    val tipItem = listTipsState.tipsList[index]
                    TipField(
                        onFieldClick = { tip ->
                            clickHandler.onTipClick(navController, tip )
                            viewModel.onSearchQueryChange(tip)
                            viewModel.getSearchTips(tip)
                        },
                        iconTip = if (tipItem.isInSearchHistory) {
                            R.drawable.ic_clock_history_20dp
                        } else {
                            R.drawable.ic_search_20dp
                        },
                        tip = tipItem.tip,
                        tipCategory = tipItem.tipCategory,
                        isDividerVisible = index < minOf(listTipsState.tipsList.size - 1, 7)
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


@Composable
fun TipField(
    modifier: Modifier = Modifier,
    onFieldClick: (String) -> Unit = {},
    @DrawableRes iconTip: Int,
    tip: String,
    tipCategory: String? = null,
    isDividerVisible: Boolean
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onFieldClick(tip) })
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconTip),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = stringResource(R.string.icon_tips),
            modifier = Modifier.padding(start = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Title2Primary(text = tip, maxLines = 2)
            if (tipCategory != null) {
                Subtitle2Primary(text = tipCategory, maxLines = 1)
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_forward_14dp),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = stringResource(R.string.next),
        )
    }

    if (isDividerVisible) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 46.dp),
            color = Tokens.TextSecondary.getColor(),
            thickness = 1.dp
        )
    }
}