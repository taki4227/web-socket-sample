package com.example.taki.web_socket_sample.web_socket.di

import android.app.Application
import com.example.taki.web_socket_sample.web_socket.TestWebSocket
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        WebSocketModule::class
    ]
)
interface WebSocketComponent {

    fun testWebSocket(): TestWebSocket

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): WebSocketComponent
    }

    companion object {
        fun factory(): Factory = DaggerWebSocketComponent.factory()
    }
}