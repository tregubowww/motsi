package com.example.motsi.core.wizard.impl.models.domain

import com.example.motsi.core.common.models.domain.SnackbarType
import com.example.motsi.core.common.presentation.validator.ValidatorType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WizardCoordinatorModel(
    val screen: Screen,
) {

    sealed class Screen{
        data class BaseScreen(val model: BaseScreenModel) : Screen()
        data class MapScreen(val model: MapScreenModel) : Screen()
    }

    data class BaseScreenModel(
        val currentStep: Int,
        val appBar: AppBar?,
        val bottomBar: BottomBar?,
        val listWidget: ImmutableList<Widget>
    )

    data class MapScreenModel(
        val idScreen: String,
        val currentStep: Int,
        val appBar: AppBar?,
        val bottomBar: BottomBar?,
        val listWidget: ImmutableList<Widget>,
        val markers: Points,
        val urlGetMarkers: String,
    ) {
        data class Points(
            val cityLocation: Point.CityLocation,
            val pointList: ImmutableList<Point> = persistentListOf(),
            val historyTipList: ImmutableList<Tip> = persistentListOf(),

            ){
            data class Point(
                val id: String,
                val typeSport: String,
                val descriptionActivity: String,
                val dateText: String,
                val locationText: String,
                val participantList: List<Participant>,
                val iconTypeSport: String,
                val colorTypeSport: String,
                val privateStatus: PrivateStatus,
                val mapData: MapData
            ) {
                enum class PrivateStatus{
                    OPEN,
                    PRIVATE
                }

                data class CityLocation(
                    val cityPoint: Pair<Double, Double>,
                    val cityZoom: Double
                )

                data class Participant(
                    val urlUserPic: String
                )
                data class MapData(
                    val locationPoint: Pair<Double, Double>,
                    val zoom: Float = 0f,
                    val azimuth: Float = 0f,
                    val tilt: Float = 0f,
                    val icon: String,
                    val color: String,
                    val description: String
                )
            }
            data class Tip(
                val type: String,
                val value: String,
                val tipTitle: String,
                val categoryTitle: String?,
                val icon: String
            )
        }
    }
    data class AppBar(
        val iconNavigation: String?,
        val title: String,
        val properties: Map<String, String>?,
        val actions: Map<String, Action>,
    )

    data class BottomBar(
        val listWidget: ImmutableList<Widget>
    )

    data class Widget(
        val id: String,
        val type: String,
        val validators: ImmutableList<ValidatorType>?,
        val properties: Map<String, String>?,
        val actions: Map<String, Action>?,
        val items: ImmutableList<Item>?,
    ) {

        data class Item(
            val id: String,
            val title: String,
            val subtitle: String?,
            val description: String?,
            val icon: String?,
            val actions: Map<String, Action>?,
            val validators:ImmutableList<ValidatorType>?,
            val properties: Map<String, String>?,
        )
    }

    sealed interface Action {
        data class Deeplink(val uri: String) : Action
        data object PreviewScreen: Action
        data class NextScreen(val properties: Map<String, String>) : Action
        data class ExitWizardFlow(val properties: Map<String, String>) : Action
        data class ShowSnackBar(
            val message: String,
            val type: SnackbarType,
            val onDismissed: Action,
            val onActionPerformed: Action
        ) : Action
    }
}
