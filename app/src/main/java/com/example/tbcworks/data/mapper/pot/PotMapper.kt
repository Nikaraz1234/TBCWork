package com.example.tbcworks.data.mapper.pot

import com.example.tbcworks.data.local.entity.PotEntity
import com.example.tbcworks.data.remote.model.pot.PotRequestDto
import com.example.tbcworks.data.remote.model.pot.PotResponseDto
import com.example.tbcworks.domain.model.Pot

fun PotRequestDto.toDomain() = Pot(
    id = id,
    title = title,
    balance = amount,
    targetAmount = targetAmount
)

fun Pot.toRequest(): PotRequestDto = PotRequestDto(
    id = this.id,
    title = this.title,
    userId = this.userId,
    amount = this.balance,
    targetAmount = this.targetAmount
)

fun PotResponseDto.toDomain(): Pot = Pot(
    id = this.id,
    title = this.title,
    userId = this.userId,
    balance = this.amount,
    targetAmount = this.targetAmount
)
fun PotEntity.toDomain(): Pot = Pot(
    id = this.id,
    title = this.title,
    userId = this.userId,
    balance = this.amount,
    targetAmount = this.targetAmount
)
fun PotEntity.toData(): PotRequestDto = PotRequestDto(
    id = this.id,
    title = this.title,
    userId = this.userId,
    amount = this.amount,
    targetAmount = this.targetAmount
)

fun Pot.toEntity(): PotEntity = PotEntity(
    id = this.id,
    title = this.title,
    userId = this.userId,
    amount = this.balance,
    targetAmount = this.targetAmount
)

fun PotResponseDto.toEntity(): PotEntity = PotEntity(
    id = this.id,
    title = this.title,
    userId = this.userId,
    amount = this.amount,
    targetAmount = this.targetAmount
)

