package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import kotlinx.coroutines.flow.Flow

interface PotRepository {
    suspend fun addPot(userId: String, pot: Pot): Flow<Resource<Unit>>
    suspend fun getPots(userId: String): Flow<Resource<List<Pot>>>
    suspend fun deletePot(userId: String, potId: String): Flow<Resource<Unit>>
    suspend fun editPot(userId: String, pot: Pot): Flow<Resource<Unit>>
    suspend fun addMoney(userId: String, potId: String, amount: Double): Flow<Resource<Unit>>
    suspend fun withdrawMoney(userId: String, potId: String, amount: Double): Flow<Resource<Unit>>
    suspend fun getUserPotsCount(userId: String): Flow<Resource<Int>>

}