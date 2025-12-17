package com.example.tbcworks.presentation.screens.pots.mapper

import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.presentation.screens.pots.model.PotModel


fun Pot.toPresentation(): PotModel = PotModel(
    id = this.id,
    title = this.title,
    userId = this.userId,
    balance = this.balance,
    targetAmount = this.targetAmount
)


fun PotModel.toDomain(): Pot = Pot(
    id = this.id,
    title = this.title,
    userId = this.userId,
    balance = this.balance,
    targetAmount = this.targetAmount
)

