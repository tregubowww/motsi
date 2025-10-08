package com.example.motsi.feature.addsportactivity.impl.presentation.choicetypesport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchField
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.core.ui.utils.toIconRes
import com.example.motsi.feature.addsportactivity.impl.models.domain.ChoiceTypeSportModel
import com.example.motsi.feature.addsportactivity.impl.models.presentation.ChoiceTypeSportScreenIntent
import com.example.motsi.core.ui.designsystem.fields.BaseIconTextField


@Composable
internal fun ChoiceTypeSportScreen(
    viewModel: ChoiceTypeSportViewModel,
    bottomNavBar: @Composable () -> Unit,
) {
    val screenState by viewModel.choiceTypeSportScreenState.collectAsState()
    LifecycleEffect(onCreate = { viewModel.initViewModel() })
    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            Success(
                model = state.data,
                viewModel = viewModel,
                bottomNavBar = bottomNavBar
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
    model: ChoiceTypeSportModel,
    viewModel: ChoiceTypeSportViewModel,
    bottomNavBar: @Composable () -> Unit
) {
    val navController = LocalAppNavController.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val choiceTypeSportScreenState by viewModel.choiceTypeSportScreenState.collectAsState()
    Scaffold(
        modifier = Modifier,
        topBar = {
            BaseAppBar(
                modifier = Modifier.background(color = Tokens.Background.getColor()),
                navigationItem = AppBarAction(
                    iconRes = R.drawable.ic_cross_outline_24dp,
                    iconTint = Tokens.IconPrimary.getColor(),
                    onClick = { navController.popBackStack() }
                ),
                title = model.title
            )
        },
        bottomBar = bottomNavBar
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Tokens.Background.getColor())
        ) {

            SearchField(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .height(45.dp),
                query = searchQuery,
                onTextChange = { text ->
                    viewModel.onChoiceTypeSportScreenIntent(
                        ChoiceTypeSportScreenIntent.FilerListTypeSport(text = text)
                    )
                },
                hint = model.searchHint,
                onKeyboardSearchButtonClick = { text ->
                    ChoiceTypeSportScreenIntent.FilerListTypeSport(text = text)
                },
                isNeedToFocused = false
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                itemsIndexed(
                    items = choiceTypeSportScreenState.listTypeSport,
                    itemContent = { index, item ->
                        BaseIconTextField(
                            onFieldClick = {
                                viewModel.onChoiceTypeSportScreenIntent(
                                    ChoiceTypeSportScreenIntent.ClickTypeSportField(name = item.name)
                                )
                            },
                            icon = item.icon.toIconRes(),
                            title = item.name,
                            subtitle = item.sportGroup,
                            isDividerVisible = index != model.listTypeSport.lastIndex
                        )
                    }
                )
            }
        }
    }
}
