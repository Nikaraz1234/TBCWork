package com.example.tbcworks.data.remote.model.transaction

data class TransactionRequestDto(
    val id: String = "",
    val senderId: String = "",
    val receiverEmail: String = "",
    val purpose: String = "",
    val value: Double = 0.0,
    val date: String = "",
    val imageUrl: String? = null
)