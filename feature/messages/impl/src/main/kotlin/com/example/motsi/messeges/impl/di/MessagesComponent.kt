package com.example.motsi.messeges.impl.di

import dagger.Component

@Component(modules = [MessagesModule::class])
internal interface  MessagesComponent: MessagesInnerApi
