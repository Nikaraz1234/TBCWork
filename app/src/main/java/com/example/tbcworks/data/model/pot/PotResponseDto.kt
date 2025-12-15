package com.example.tbcworks.data.model.pot

import com.google.firebase.firestore.DocumentId


data class PotResponseDto(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val targetAmount: Double = 0.0
)