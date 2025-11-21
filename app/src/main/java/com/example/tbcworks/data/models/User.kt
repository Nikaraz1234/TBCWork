package com.example.tbcworks.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val image: String? = null,
    val owner: String,
    @Json(name = "last_message")
    val lastMessage: String,
    @Json(name = "last_active")
    val lastActive: String,
    @Json(name = "unread_messages")
    val unreadMessages: Int,
    @Json(name = "is_typing")
    val isTyping: Boolean,
    @Json(name = "laste_message_type")
    val lastMessageType: MessageType
)


enum class MessageType {
    @Json(name = "text")
    TEXT,
    @Json(name = "file")
    FILE,
    @Json(name = "voice")
    VOICE
}