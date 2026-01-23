package com.example.composeapp.data.service

import com.example.composeapp.data.dto.ChatDto
import retrofit2.http.GET

interface ChatService {
    @GET("chats")
    suspend fun getCategories(): List<ChatDto>
}