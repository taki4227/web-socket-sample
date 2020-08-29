package com.example.taki.web_socket_sample

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.taki.web_socket_sample.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @Inject
    lateinit var repository: Repository

    private val socket by lazy {
        val socket = IO.socket("url")

        socket.on(Socket.EVENT_CONNECT) { Log.d(TAG, "connect") }
            .on(Socket.EVENT_DISCONNECT) { Log.d(TAG, "disconnect") }
            .on(Socket.EVENT_CONNECT_ERROR) { Log.d(TAG, "connect error") }
            .on(Socket.EVENT_CONNECT_TIMEOUT) { Log.d(TAG, "connect timeout") }

        socket
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    private var webSocket: WebSocket? = null
    private var isConnect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.socketIoConnectButton.setOnClickListener {
            socket.connect()
        }

        binding.socketIoSendButton.setOnClickListener {
            if (socket.connected()) {
                socket.emit("event_name", "Send text")
            }
        }

        binding.socketIoConnectButton.setOnClickListener {
            val request = Request.Builder().url("url").build()
            webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    Log.d(TAG, "onOpen")
                    isConnect = true
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    Log.d(TAG, "onMessage string")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    super.onMessage(webSocket, bytes)
                    Log.d(TAG, "onMessage byte")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosing(webSocket, code, reason)
                    Log.d(TAG, "onClosing")
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    Log.d(TAG, "onClosed")
                    isConnect = false
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    Log.e(TAG, "onFailure", t)
                }
            })

            okHttpClient.dispatcher.executorService.shutdown()
        }

        binding.socketIoSendButton.setOnClickListener {
            if (isConnect) {
                webSocket?.send("send text")
            }
        }

        repository.test()
    }

    override fun onDestroy() {
        socket.off()
        if (socket.connected()) {
            socket.disconnect()
        }
        if (isConnect) {
            // status code see: https://tools.ietf.org/html/rfc6455#section-7.4
            webSocket?.close(1000, "connection close")
        }
        super.onDestroy()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}