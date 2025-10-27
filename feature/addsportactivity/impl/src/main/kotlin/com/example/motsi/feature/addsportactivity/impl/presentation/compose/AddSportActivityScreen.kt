package com.example.motsi.feature.addsportactivity.impl.presentation.compose

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.addsportactivity.impl.models.presentation.AddSportActivityDestination

@Composable
internal fun AddSportActivityScreen(
    viewModel: AddSportActivityViewModel,
    data: AddSportActivityDestination.AddSportActivityData,
) {
//    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalAppNavController.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            BaseAppBar(
                navigationItem = AppBarAction(
                    iconRes = R.drawable.ic_cross_24dp,
                    iconTint = Tokens.IconPrimary.getColor(),
                    onClick = { navController.popBackStack() }
                ),
            )
        },
    ) { padding ->

    }
}
