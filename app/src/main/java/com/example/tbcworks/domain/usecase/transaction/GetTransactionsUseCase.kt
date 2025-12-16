package com.example.tbcworks.domain.usecase.transaction

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<List<Transaction>>> {
        return repository.getTransactions(userId)
    }
}
