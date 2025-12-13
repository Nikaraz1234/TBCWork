package com.example.tbcworks.presentation.screens.pots

import com.example.tbcworks.presentation.screens.pots.model.PotModel

data class PotState(
    val pots: List<PotModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)