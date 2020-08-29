package com.example.taki.web_socket_sample.di

import com.example.taki.web_socket_sample.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AppModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = Repository()
}