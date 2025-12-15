package com.example.tbcworks.domain.usecase.transaction


import com.example.tbcworks.domain.repository.TransactionRepository
import javax.inject.Inject

class SendTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String? = null
    ) {
        repository.sendTransaction(
            senderId = senderId,
            receiverEmail = receiverEmail,
            amount = amount,
            purpose = purpose,
            imageUrl = imageUrl
        )
    }
}

