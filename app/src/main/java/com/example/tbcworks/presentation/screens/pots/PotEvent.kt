package com.example.tbcworks.presentation.screens.pots

import com.example.tbcworks.presentation.screens.pots.model.PotModel

sealed class PotEvent {
    object LoadPots : PotEvent()
    data class AddPot(
        val title: String,
        val targetAmount: Double,
    ) : PotEvent()
}