package com.example.motsi.feature.userprofile.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.userprofile.api.UserProfileGraph
import com.example.motsi.feature.userprofile.impl.di.UserProfileHolder
import com.example.motsi.feature.userprofile.impl.di.UserProfileInternalApi
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileDestination
import com.example.motsi.feature.userprofile.impl.presentation.compose.UserProfileScreen
import javax.inject.Inject

class UserProfileNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<UserProfileInternalApi>(UserProfileHolder) {
            navigation<UserProfileGraph>(startDestination = UserProfileDestination) {
                val api = getFeatureApi<UserProfileInternalApi>()
                val factory = api.viewModelFactory()

                composable<UserProfileDestination> {
                    val viewModel: UserProfileViewModel = viewModel(factory = factory)

                    UserProfileScreen(
                        viewModel = viewModel,
                        bottomNavBar = bottomNavBar
                    )
                }
            }
        }
    }
}