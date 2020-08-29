package com.example.taki.web_socket_sample

import com.example.taki.web_socket_sample.web_socket.Message
import com.example.taki.web_socket_sample.web_socket.TestWebSocket
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebSocketRepository @Inject constructor(
    private val testWebSocket: TestWebSocket
) {

    suspend fun observeWebSocketEvent(): Flow<WebSocket.Event> {
        return testWebSocket.observeWebSocketEvent()
    }

    suspend fun sendMessage(text: String) {
        testWebSocket.sendMessage(Message(text))
    }
}