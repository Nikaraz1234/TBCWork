package com.example.tbcworks.domain.usecase.transaction

import com.example.tbcworks.domain.repository.TransactionRepository
import javax.inject.Inject


class RefreshTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: String) {
        repository.refreshTransactions(userId)
    }
}