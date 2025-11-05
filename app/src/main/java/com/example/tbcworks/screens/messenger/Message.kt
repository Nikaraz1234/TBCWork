package com.example.tbcworks.screens.messenger

import java.time.LocalDateTime


data class Message(
    val id: Int,
    val content: String,
    val sentTime: LocalDateTime = LocalDateTime.now()
)

