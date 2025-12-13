package com.example.tbcworks.data.model.pot

import com.google.firebase.firestore.Exclude

data class PotRequestDto(
    @get:Exclude val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val progressPercent: Double = 0.0,
    val targetAmount: Double = 0.0,
)
