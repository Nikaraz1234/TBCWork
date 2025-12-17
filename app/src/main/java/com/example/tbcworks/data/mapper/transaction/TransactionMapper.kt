package com.example.tbcworks.data.mapper.transaction

import com.example.tbcworks.data.local.entity.TransactionEntity
import com.example.tbcworks.data.remote.model.transaction.TransactionRequestDto
import com.example.tbcworks.data.remote.model.transaction.TransactionResponseDto
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

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)
fun TransactionEntity.toData() = TransactionRequestDto(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)
fun Transaction.toData() = TransactionRequestDto(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)
fun TransactionResponseDto.toEntity() = TransactionEntity(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl,
)
fun TransactionRequestDto.toEntity() = TransactionEntity(
    id = id,
    senderId = senderId,
    receiverEmail = receiverEmail,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)