package com.example.composeapp.ui.screen.dashboard.mapper

import com.example.composeapp.domain.model.Chat
import com.example.composeapp.ui.screen.dashboard.model.ChatModel

fun Chat.toPresentation(): ChatModel {
    val type = when (lastMessageType.lowercase()) {
        "text" -> ChatModel.ChatType.TEXT
        "file" -> ChatModel.ChatType.FILE
        "voice" -> ChatModel.ChatType.VOICE
        else -> ChatModel.ChatType.TEXT
    }

    return ChatModel(
        id = id,
        image = image,
        owner = owner,
        lastMessage = lastMessage,
        lastActive = lastActive,
        unreadMessages = unreadMessages,
        isTyping = isTyping,
        lastMessageType = type
    )
}

fun List<Chat>.toPresentation(): List<ChatModel> = map { it.toPresentation() }
