package com.example.motsi.messeges.impl.di

import com.example.motsi.core.di.holder.InitHolder

internal object MessagesHolder : InitHolder<MessagesInternalApi>() {
    override fun create(): MessagesInternalApi =
        DaggerMessagesComponent.factory().create()
}