package com.example.tbcworks.presentation.screens.transaction.model

data class TransactionModel(
    val id: String,
    val name: String,
    val purpose: String,
    val value: String,
    val date: String,
    val imageUrl: String? = null
)
