package com.example.tbcworks.domain.usecase.transaction

import com.example.tbcworks.domain.repository.TransactionRepository
import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
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
    ): Flow<Resource<Unit>> {
        return repository.sendTransaction(
            senderId = senderId,
            receiverEmail = receiverEmail,
            amount = amount,
            purpose = purpose,
            imageUrl = imageUrl
        )
    }
}
