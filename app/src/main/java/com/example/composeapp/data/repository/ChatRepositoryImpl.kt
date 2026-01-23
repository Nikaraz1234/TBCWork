package com.example.composeapp.data.repository

import com.example.composeapp.data.common.HandleResponse
import com.example.composeapp.data.extension.asResource
import com.example.composeapp.data.mapper.toDomain
import com.example.composeapp.data.service.ChatService
import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Chat
import com.example.composeapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val service: ChatService,
    private val handleResponse: HandleResponse
) : ChatRepository {

    override fun getUsers(): Flow<Resource<List<Chat>>> {
        return handleResponse.safeApiCall {
            val response = service.getCategories() // <- you might want service.getChats() instead
            println("API raw response: $response") // <-- log the raw API response
            response
        }.map { apiResult ->
            println("Mapping response to domain: $apiResult") // <-- log before mapping
            apiResult.asResource { users ->
                val mapped = users.map { it.toDomain() }
                println("Mapped to domain: $mapped") // <-- log after mapping
                mapped
            }
        }
    }
}