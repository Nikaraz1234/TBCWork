package com.example.composeapp.domain.usecase

import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Chat
import com.example.composeapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<Resource<List<Chat>>> {
        return repository.getUsers()
    }
}