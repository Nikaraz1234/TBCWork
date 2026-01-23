package com.example.composeapp.data.mapper

import com.example.composeapp.data.dto.ChatDto
import com.example.composeapp.domain.model.Chat

fun ChatDto.toDomain(): Chat {
    return Chat(
        id = this.id,
        image = this.image,
        owner = this.owner,
        lastMessage = this.lastMessage,
        lastActive = this.lastActive,
        unreadMessages = this.unreadMessages,
        isTyping = this.isTyping,
        lastMessageType = this.lastMessageType
    )
}
