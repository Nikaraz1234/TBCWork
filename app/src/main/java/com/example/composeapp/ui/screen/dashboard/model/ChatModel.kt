package com.example.composeapp.ui.screen.dashboard.model

data class ChatModel(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: ChatType
){
    enum class ChatType{
        TEXT,
        FILE,
        VOICE
    }
}
