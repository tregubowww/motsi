package com.example.motsi.di

import com.example.motsi.domain.MainInteractor
import com.example.motsi.domain.MainInteractorImpl
import dagger.Module
import dagger.Provides


@Module
internal class MainActivityModule
{
    @Provides
    fun provideMainInteractor(): MainInteractor =
        MainInteractorImpl()
}