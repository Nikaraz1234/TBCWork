package com.example.tbcworks.presentation.screens.pots.mapper

import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.presentation.screens.pots.model.PotModel

fun Pot.toPresentation(): PotModel = PotModel(
    id = id,
    title = title,
    amount = amount,
    progressPercent = progressPercent,
    targetAmount = targetAmount,
)

fun PotModel.toDomain(): Pot = Pot(
    id = id,
    title = title,
    amount = amount,
    progressPercent = progressPercent,
    targetAmount = targetAmount,
)