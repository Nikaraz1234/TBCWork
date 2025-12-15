package com.example.tbcworks.presentation.screens.transaction

data class TransactionState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null
)