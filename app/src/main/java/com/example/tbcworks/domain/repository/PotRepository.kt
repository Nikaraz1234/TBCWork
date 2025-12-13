package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import kotlinx.coroutines.flow.Flow

interface PotRepository {
    suspend fun addPot(pot: Pot): Flow<Resource<Unit>>
    suspend fun getPots(): Flow<Resource<List<Pot>>>
    suspend fun deletePot(potId: String): Flow<Resource<Unit>>
}
