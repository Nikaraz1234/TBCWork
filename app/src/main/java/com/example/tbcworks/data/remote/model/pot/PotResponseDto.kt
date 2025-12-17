package com.example.tbcworks.data.remote.model.pot

import com.google.firebase.firestore.DocumentId


data class PotResponseDto(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val targetAmount: Double = 0.0
)