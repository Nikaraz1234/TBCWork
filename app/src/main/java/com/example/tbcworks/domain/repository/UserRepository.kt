package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserBalance(userId: String): Flow<Resource<Double>>
    suspend fun addMoneyToUser(userId: String, amount: Double): Flow<Resource<Unit>>
    suspend fun withdrawMoneyFromUser(userId: String, amount: Double): Flow<Resource<Unit>>
}