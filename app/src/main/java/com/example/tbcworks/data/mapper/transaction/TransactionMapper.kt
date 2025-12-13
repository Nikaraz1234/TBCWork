package com.example.tbcworks.data.mapper.transaction

import com.example.tbcworks.data.model.transaction.TransactionRequestDto
import com.example.tbcworks.data.model.transaction.TransactionResponseDto
import com.example.tbcworks.domain.model.Transaction

fun Transaction.toRequest(): TransactionRequestDto = TransactionRequestDto(
    id = id,
    name = name,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)

fun TransactionResponseDto.toDomain(): Transaction = Transaction(
    id = id,
    name = name,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)