package com.example.tbcworks.presentation.screens.transaction

import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel

data class TransactionState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionModel> = emptyList(),
    val filteredTransactions: List<TransactionModel> = emptyList(),
    val sortField: TransactionModel.SortField = TransactionModel.SortField.DATE,
    val sortAscending: Boolean = false,
    val message: String? = null,
    val error: String? = null
)