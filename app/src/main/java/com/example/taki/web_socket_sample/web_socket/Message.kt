package com.example.taki.web_socket_sample.web_socket

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val text: String
)