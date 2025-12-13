package com.example.tbcworks.domain.model

data class Transaction(
    val id: String = "",
    val name: String = "",
    val purpose: String = "",
    val value: String = "",
    val date: String = "",
    val imageUrl: String? = null
)
