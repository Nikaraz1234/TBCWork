package com.example.tbcworks.domain.repository

interface TransactionRepository {
    suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String? = null
    )
}

