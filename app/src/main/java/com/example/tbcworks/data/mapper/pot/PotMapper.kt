package com.example.tbcworks.data.mapper.pot

import com.example.tbcworks.data.model.pot.PotRequestDto
import com.example.tbcworks.data.model.pot.PotResponseDto
import com.example.tbcworks.domain.model.Pot

fun PotRequestDto.toDomain() = Pot(
    id = id,
    title = title,
    balance = amount,
    targetAmount = targetAmount
)

fun Pot.toRequest() = PotRequestDto(
    id = id,
    title = title,
    amount = balance,
    targetAmount = targetAmount
)
fun PotResponseDto.toDomain() = Pot(
    id = id,
    title = title,
    balance = amount,
    targetAmount = targetAmount
)

