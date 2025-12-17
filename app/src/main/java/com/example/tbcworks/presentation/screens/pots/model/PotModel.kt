package com.example.tbcworks.presentation.screens.pots.model

data class PotModel(
    val id: String,
    val title: String,
    val userId: String = "",
    val balance: Double,
    val targetAmount: Double,
){
    val progressPercent: Int
        get() = if (targetAmount == 0.0) 0
        else ((balance / targetAmount) * 100).toInt()
}
