package com.example.taki.web_socket_sample.di

import com.example.taki.web_socket_sample.WebSocketRepository
import com.example.taki.web_socket_sample.web_socket.TestWebSocket
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AppModule {

    @Singleton
    @Provides
    fun provideWebSocketRepository(testWebSocket: TestWebSocket): WebSocketRepository =
        WebSocketRepository(testWebSocket)
}