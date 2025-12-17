package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String? = null
    ): Flow<Resource<Unit>>

    suspend fun getTransactions(userId: String): Flow<Resource<List<Transaction>>>

    suspend fun refreshTransactions(userId: String)
}

