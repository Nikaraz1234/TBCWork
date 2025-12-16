package com.example.tbcworks.presentation.screens.transaction

import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel

sealed class TransactionEvent {
    data class SendTransaction(
        val receiverEmail: String,
        val amount: Double,
        val purpose: String,
        val imageUrl: String? = null
    ) : TransactionEvent()

    object LoadTransactions : TransactionEvent()

    data class SearchTransactions(val query: String) : TransactionEvent()
    data class SortTransactions(val field: TransactionModel.SortField, val ascending: Boolean) : TransactionEvent()
}
