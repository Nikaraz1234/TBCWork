package com.example.tbcworks.presentation.screens.transaction.model


data class TransactionModel(
    val id: String,
    val senderId: String,
    val receiverEmail: String,
    val purpose: String,
    val value: Double,
    val date: String,
    val imageUrl: String?
) {
    enum class SortField {
        DATE,
        AMOUNT
    }
}
