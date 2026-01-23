package com.example.composeapp.domain.repository

import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getUsers(): Flow<Resource<List<Chat>>>
}