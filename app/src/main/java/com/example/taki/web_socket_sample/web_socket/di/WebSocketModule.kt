package com.example.taki.web_socket_sample.web_socket.di

import android.app.Application
import com.example.taki.web_socket_sample.web_socket.TestWebSocket
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebSocketModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideLifecycle(app: Application): Lifecycle {
        return AndroidLifecycle.ofApplicationForeground(app)
        // connect only logged in
        // see https://github.com/Tinder/Scarlet/blob/master/demo/src/main/java/com/tinder/app/echo/inject/EchoBotComponent.kt
//            .combineWith()
    }

    @Singleton
    @Provides
    fun provideScarlet(okHttpClient: OkHttpClient, lifecycle: Lifecycle): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory("https://test.com/"))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(
                MoshiMessageAdapter.Factory(
                    Moshi.Builder()
                        .build()
                )
            )
            .addStreamAdapterFactory(FlowStreamAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideTestWebSocket(scarlet: Scarlet): TestWebSocket {
        return scarlet.create(TestWebSocket::class.java)
    }
}