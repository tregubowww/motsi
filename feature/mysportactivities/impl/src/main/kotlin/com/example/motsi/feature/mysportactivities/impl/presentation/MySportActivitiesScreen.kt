package com.example.motsi.feature.mysportactivities.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.designsystem.buttons.BaseButton
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Title1Secondary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.core.wizard.api.WizardGraph
import com.example.motsi.feature.mysportactivities.impl.models.domain.MySportActivitiesModel
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesIntent
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesScreenEffect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun MySportActivitiesScreen(
    viewModel: MySportActivitiesViewModel,
    bottomNavBar: @Composable () -> Unit,
) {

    val navController = LocalAppNavController.current
    val screenState by viewModel.screenState.collectAsState()

    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {

            Scaffold(
                modifier = Modifier.background(Tokens.Background.getColor()),
                topBar = {
                    BaseAppBar(
                        modifier = Modifier.background(color = Tokens.Background.getColor()),
                        title = state.data.appBar.title
                    )
                },
                bottomBar = {
                    Column {
                        BaseButton(
                            modifier = Modifier
                                .background(Tokens.Background.getColor())
                                .padding(16.dp),
                            text = state.data.addSportActivity.bottomBarButtonTitle,
                            color = Tokens.BackgroundBrand,
                            onClick = {
                                viewModel.dispatch(MySportActivitiesIntent.AddSportActivity)
                            }
                        )
                        bottomNavBar.invoke()
                    }
                }
            ) { padding ->
                CollectEffect(viewModel.effect) { effect ->
                    when (effect) {
                        is MySportActivitiesScreenEffect.OpenAddSportActivityScreen -> {
                            navController.navigate(WizardGraph(effect.url))
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Pager(state.data.pageList)
                }
            }
        }

        is LoadingState.Error -> {
//            Error()
        }

        else -> Unit
    }
}

@Composable
internal fun Pager(pages: ImmutableList<MySportActivitiesModel.Page>) {
    val pagerState = rememberPagerState { pages.size }
    val coroutine = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Tokens.Background.getColor())
    ) {

        PagerIndicator(
            pages = pages.map { it.title },
            pagerState = pagerState,
            onTabClick = { index ->
                coroutine.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Страница $page")
            }
        }
    }
}

@Composable
fun PagerIndicator(
    pages: List<String>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
) {
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            pages.forEachIndexed { index, label ->
                val isActive = pagerState.currentPage == index
                if (isActive) {
                    Title1Primary(text = label, modifier = Modifier.clickable(onClick = {
                        onTabClick.invoke(index)
                    }))
                } else {
                    Title1Secondary(text = label, modifier = Modifier.clickable(onClick = {
                        onTabClick.invoke(index)
                    }))
                }
            }
        }

        LinePagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun LinePagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    height: Dp = 4.dp,
    inactiveColor: Color = Tokens.BackgroundPrimary.getColor().copy(alpha = 0.2f),
    activeColor: Color = Tokens.BackgroundPrimary.getColor(),
    cornerRadius: Dp = 4.dp,
) {
    BoxWithConstraints(modifier = modifier) {

        val totalWidthPx = constraints.maxWidth.toFloat()
        val indicatorWidthPx = totalWidthPx / pageCount

        val density = LocalDensity.current

        val progress by remember {
            derivedStateOf {
                val page = pagerState.currentPage
                val offset = pagerState.currentPageOffsetFraction
                (page + offset).coerceIn(0f, pageCount - 1f)
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(height)
                .background(inactiveColor, RoundedCornerShape(cornerRadius))
        )

        val offsetDp = with(density) { (indicatorWidthPx * progress).toDp() }
        val itemWidthDp = with(density) { indicatorWidthPx.toDp() }

        Box(
            Modifier
                .offset(x = offsetDp)
                .width(itemWidthDp)
                .height(height)
                .background(activeColor, RoundedCornerShape(cornerRadius))
        )
    }
}
