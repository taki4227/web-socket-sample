package com.example.taki.web_socket_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.taki.web_socket_sample.databinding.ActivityMainBinding
import io.socket.client.IO
import io.socket.client.Socket

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val socket by lazy {
        val socket = IO.socket("url")

        socket.on(Socket.EVENT_CONNECT) { Log.d(TAG, "connect") }
            .on(Socket.EVENT_DISCONNECT) { Log.d(TAG, "disconnect") }
            .on(Socket.EVENT_CONNECT_ERROR) { Log.d(TAG, "connect error") }
            .on(Socket.EVENT_CONNECT_TIMEOUT) { Log.d(TAG, "connect timeout") }

        socket
    }

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
    }

    override fun onDestroy() {
        socket.off()
        if (socket.connected()) {
            socket.disconnect()
        }
        super.onDestroy()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}