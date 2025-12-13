package com.example.tbcworks.presentation.screens.pots.model

data class PotModel(
    val id: String,
    val title: String,
    val amount: Double,
    val progressPercent: Double,
    val targetAmount: Double,
)
