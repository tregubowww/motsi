package com.example.motsi.feature.addsportactivity.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.MotsiNavType
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.addsportactivity.api.AddSportActivityGraph
import com.example.motsi.feature.addsportactivity.impl.di.AddSportActivityHolder
import com.example.motsi.feature.addsportactivity.impl.di.AddSportActivityInternalApi
import com.example.motsi.feature.addsportactivity.impl.models.presentation.AddSportActivityDestination
import com.example.motsi.feature.addsportactivity.impl.models.presentation.ChoiceTypeSportDestination
import com.example.motsi.feature.addsportactivity.impl.presentation.compose.AddSportActivityScreen
import com.example.motsi.feature.addsportactivity.impl.presentation.compose.AddSportActivityViewModel
import com.example.motsi.feature.addsportactivity.impl.presentation.choicetypesport.ChoiceTypeSportScreen
import com.example.motsi.feature.addsportactivity.impl.presentation.choicetypesport.ChoiceTypeSportViewModel
import javax.inject.Inject
import kotlin.reflect.typeOf

class AddSportActivityNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<AddSportActivityInternalApi>(AddSportActivityHolder) {
            navigation<AddSportActivityGraph>(startDestination = ChoiceTypeSportDestination) {
                val api = getFeatureApi<AddSportActivityInternalApi>()
                val factory = api.viewModelFactory()

                composable<ChoiceTypeSportDestination> {
                    val viewModel: ChoiceTypeSportViewModel = viewModel(factory = factory)

                    ChoiceTypeSportScreen(
                        viewModel = viewModel,
                        bottomNavBar = bottomNavBar,
                    )
                }

//                костыль для сериализации AddSportActivityFilterData может быть в более новых версиях compose navigation  поправится и добавится возможность использовать разные типы
                val addSportActivityDataNavType = MotsiNavType(AddSportActivityDestination.AddSportActivityData.serializer())
                composable<AddSportActivityDestination>( typeMap = mapOf(typeOf<AddSportActivityDestination.AddSportActivityData>() to addSportActivityDataNavType)) { entry ->

                    val args = entry.toRoute<AddSportActivityDestination>()
                    val viewModel: AddSportActivityViewModel = viewModel(factory = factory)

                    AddSportActivityScreen(
                        viewModel = viewModel,
                        data = args.data
                    )
                }
            }
        }
    }
}
