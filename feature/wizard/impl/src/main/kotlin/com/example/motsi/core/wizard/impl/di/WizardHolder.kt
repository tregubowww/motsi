package com.example.motsi.core.wizard.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder


internal object WizardHolder : InitHolder<WizardInternalApi>() {
    override fun create(): WizardInternalApi =
        DaggerWizardComponent.factory().create(NetworkHolder.getApi())
}