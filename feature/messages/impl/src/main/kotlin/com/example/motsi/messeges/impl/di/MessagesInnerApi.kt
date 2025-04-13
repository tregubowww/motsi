package com.example.motsi.messeges.impl.di

import com.example.motsi.messages.api.di.MessagesApi
import com.example.motsi.messeges.impl.presentation.MessagesViewModel


internal interface MessagesInnerApi
    : MessagesApi
{
    val viewModel: MessagesViewModel
}