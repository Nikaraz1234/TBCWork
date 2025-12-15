package com.example.tbcworks.presentation.screens.pots

import com.example.tbcworks.presentation.screens.pots.model.PotModel

sealed class PotEvent {
    object LoadPots : PotEvent()
    data class DeletePot(val pot: PotModel) : PotEvent()
    data class AddPot(val title: String, val targetAmount: Double) : PotEvent()
    data class EditPot(val pot: PotModel) : PotEvent()
    data class AddMoney(val pot: PotModel, val amount: Double) : PotEvent()
    data class WithdrawMoney(val pot: PotModel, val amount: Double) : PotEvent()
}