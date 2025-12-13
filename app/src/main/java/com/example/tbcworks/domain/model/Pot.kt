package com.example.tbcworks.domain.model

data class Pot(
    val id: String,
    val title: String,
    val amount: Double,
    val progressPercent: Double,
    val targetAmount: Double,
)