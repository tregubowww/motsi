package com.example.motsi.core.wizard.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder
import com.example.motsi.core.wrappers.di.WrappersCoreHolder


internal object WizardHolder : InitHolder<WizardInternalApi>() {
    override fun create(): WizardInternalApi =
        DaggerWizardComponent.factory().create(NetworkHolder.getApi(), WrappersCoreHolder.getApi())
}