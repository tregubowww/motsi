package com.example.motsi.feature.search.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchField
import com.example.motsi.core.ui.designsystem.fields.BaseIconTextField
import com.example.motsi.core.ui.theming.SubtitleBrand
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.core.ui.utils.toIconRes
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListIntent
import com.example.motsi.feature.search.impl.presentation.SearchTipsViewModel

@Composable
internal fun SearchTipsScreen(
    viewModel: SearchTipsViewModel,
    bottomNavBar: @Composable () -> Unit,
    entryData: SearchTipsDestination.EntryData,
) {

    LifecycleEffect(onCreate = { viewModel.initViewModel(entryData) })
    val navController = LocalAppNavController.current
    val listTipsState by viewModel.tipListState.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TipListAppBar(
                viewModel,
                searchHint = entryData.searchHint,
                navController = navController
            )
        },
        bottomBar = bottomNavBar
    ) { padding ->

        when (val state = listTipsState.loadingState) {
            is LoadingState.Loading -> {

            }

            is LoadingState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .imePadding()
                        .background(color = Tokens.Background.getColor())
                ) {
                    itemsIndexed(
                        items = state.data.tipList,
                        itemContent = { index, item ->
                            BaseIconTextField(
                                onFieldClick = {
                                    viewModel.onTipListIntent(
                                        SearchTipListIntent.TipClick(
                                            navController,
                                            item.type,
                                            item.value
                                        )
                                    )
                                },
                                icon = item.icon.toIconRes(),
                                title = item.tipTitle,
                                subtitle = item.сategoryTitle,
                                isDividerVisible = index != state.data.tipList.lastIndex
                            )
                        }
                    )
                }
            }

            is LoadingState.Error -> {

            }

            else -> {
                //nothing
            }
        }


    }
}

@Composable
private fun TipListAppBar(
    viewModel: SearchTipsViewModel,
    searchHint: String,
    navController: NavHostController,
) {
    val searchQuery by viewModel.searchQuery.collectAsState()

    Row(
        modifier = Modifier
            .background(color = Tokens.Background.getColor())
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(58.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            query = searchQuery,
            onTextChange = { text -> viewModel.onTipListIntent(SearchTipListIntent.OnSearchQueryChange(text)) },
            hint = searchHint,
            onKeyboardSearchButtonClick = { text ->
                viewModel.onTipListIntent(
                    SearchTipListIntent.TipClick(navController, null, text)
                )
            }
        )

        SubtitleBrand(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable(onClick = {
                    viewModel.onTipListIntent(
                        SearchTipListIntent.BackClick(navController)
                    )
                }),
            text = stringResource(R.string.cancel)
        )
    }
}

