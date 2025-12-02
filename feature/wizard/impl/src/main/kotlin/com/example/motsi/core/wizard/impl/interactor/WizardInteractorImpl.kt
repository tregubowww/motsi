package com.example.motsi.core.wizard.impl.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.presentation.validator.ValidatorType
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel.Action
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel.AppBar
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel.Widget
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel.Widget.Item
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class WizardInteractorImpl @Inject constructor() :
    WizardInteractor {

    override suspend fun getNextScreen(
        properties: Map<String, String>,
        step: Int
    ): ResultWrapper<WizardScreenModel, MotsiError> {
        return when {
            step == 1 -> {
                ResultWrapper.Success(
                    WizardScreenModel(
                        currentStep = 1,
                        appBar = AppBar(
                            title = "Вид спорта",
                            iconNavigation = "ic_cross_24",
                            actions = mapOf(
                                "NAVIGATION_ACTION_KEY" to Action.ExitWizardFlow(
                                    properties = mapOf("currentStep" to "1")
                                )
                            )
                        ),
                        listWidget = persistentListOf(
                            Widget(
                                id = "1",
                                type = "IconTextWidget",
                                validators = null,
                                properties = mapOf(
                                    "TITLE_KEY" to "Соревнования",
                                    "ICON_KEY" to "ic_filter_24",
                                    "DIVIDER_FLAG_KEY" to "true"
                                ),
                                items = null,
                                actions = mapOf(
                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                        properties = mapOf("currentStep" to "2")
                                    )
                                )
                            ),
                            Widget(
                                id = "2",
                                type = "IconTextWidget",
                                validators = null,
                                properties = mapOf(
                                    "TITLE_KEY" to "Соревнования",
                                    "ICON_KEY" to "ic_filter_24",
                                    "DIVIDER_FLAG_KEY" to "false"
                                ),
                                items = null,
                                actions = mapOf(
                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                        properties = mapOf("currentStep" to "2")
                                    )
                                )
                            ),

                        ),
                        bottomBar = null
                    )
                )
            }

            step == 2 -> {
                ResultWrapper.Success(
                    WizardScreenModel(
                        currentStep = 2,
                        appBar = AppBar(
                            title = "Вид спорта",
                            iconNavigation = "ic_back_24",
                            actions = mapOf(
                                "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                            )
                        ),
                        listWidget = persistentListOf(
                            Widget(
                                id = "2",
                                type = "SearchWizardWidget",
                                validators = null,
                                actions = null,
                                properties = null,
                                items = persistentListOf(
                                    Item(
                                        id = "1",
                                        title = "Соревнования",
                                        icon = "ic_filter_24",
                                        actions = mapOf(
                                            "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                properties = mapOf("id" to "1")
                                            )
                                        ),
                                        subtitle = null,
                                        description = null,
                                        validators = null,
                                    )
                                )
                            )
                        ),
                        bottomBar = null
                    )
                )
            }

            step == 3 -> {
                ResultWrapper.Success(
                    WizardScreenModel(
                        currentStep = 3,
                        appBar = AppBar(
                            title = "Тренировка",
                            iconNavigation = "ic_back_24",
                            actions = mapOf(
                                "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                            )
                        ),
                        listWidget = persistentListOf(
                            Widget(
                                id = "1й",
                                type = "SearchWizardWidget",
                                validators = persistentListOf(
                                    ValidatorType.Required(
                                        "",
                                        "Поле обязательно для заполнения"
                                    )
                                ),
                                actions = null,
                                properties = null,
                                items = persistentListOf(
                                    Item(
                                        id = "1",
                                        title = "Соревнования",
                                        icon = "ic_filter_24",
                                        actions = mapOf(),
                                        subtitle = null,
                                        description = null,
                                        validators = null,
                                    )
                                )
                            )
                        ),
                        bottomBar = WizardScreenModel.BottomBar(
                            persistentListOf(
                                Widget(
                                    id = "1",
                                    type = "ButtonWizardWidget",
                                    validators = null,
                                    properties = mapOf("TITLE_KEY" to "Создать тренировку"),
                                    items = null,
                                    actions = mapOf(
                                        "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                            properties = mapOf("currentStep" to "3")
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }

            else -> {
                ResultWrapper.Success(
                    WizardScreenModel(
                        currentStep = 4,
                        appBar = AppBar(
                            title = "Статус",
                            iconNavigation = null,
                            actions = mapOf(
                                "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                            )
                        ),
                        listWidget = persistentListOf(
                            Widget(
                                id = "1",
                                type = "ButtonWizardWidget",
                                validators = null,
                                properties = mapOf("TITLE_KEY" to "Перейти в мои активности"),
                                items = null,
                                actions = mapOf(
                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.ExitWizardFlow(
                                        properties = mapOf("currentStep" to "4")
                                    )
                                )
                            )
                        ),
                        bottomBar = null
                    )
                )
            }
        }
    }


    override suspend fun getItems(query: String, urlGetSearchItems: String) =
        ResultWrapper.Success(
            persistentListOf(
                Item(
                    id = "1",
                    title = "теннис большой",
                    actions = mapOf(
                        "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                            properties = mapOf("id" to "1")
                        )
                    ),
                    icon = null,
                    subtitle = null,
                    description = null,
                    validators = null,
                )
            )
        )
}