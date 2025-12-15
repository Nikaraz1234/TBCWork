package com.example.tbcworks.presentation.screens.transaction

sealed class TransactionEvent {
    data class SendTransaction(
        val receiverEmail: String,
        val amount: Double,
        val purpose: String,
        val imageUrl: String? = null
    ) : TransactionEvent()
}
