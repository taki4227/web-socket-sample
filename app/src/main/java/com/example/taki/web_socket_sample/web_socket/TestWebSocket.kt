package com.example.taki.web_socket_sample.web_socket

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface TestWebSocket {

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>

    @Send
    fun sendMessage(message: Message)
}