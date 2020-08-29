package com.example.taki.web_socket_sample.di

import android.app.Application
import com.example.taki.web_socket_sample.web_socket.TestWebSocket
import com.example.taki.web_socket_sample.web_socket.di.WebSocketComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WebSocketComponentModule {
    @Provides
    @Singleton
    fun provideTestWebSocket(application: Application): TestWebSocket {
        return WebSocketComponent.factory()
            .create(application)
            .testWebSocket()
    }
}