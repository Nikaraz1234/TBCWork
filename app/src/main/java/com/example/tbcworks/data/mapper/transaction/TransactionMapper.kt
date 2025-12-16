package com.example.tbcworks.data.mapper.transaction

import com.example.tbcworks.data.model.transaction.TransactionRequestDto
import com.example.tbcworks.data.model.transaction.TransactionResponseDto
import com.example.tbcworks.domain.model.Transaction


fun Transaction.toRequestDto() = TransactionRequestDto(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)


fun TransactionResponseDto.toDomain() = Transaction(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl,
)
fun TransactionRequestDto.toDomain() = Transaction(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)
