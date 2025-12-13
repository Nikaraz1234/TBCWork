package com.example.tbcworks.data.model.transaction

data class TransactionRequestDto(
    val id: String = "",
    val name: String = "",
    val purpose: String = "",
    val value: String = "",
    val date: String = "",
    val imageUrl: String? = null
)