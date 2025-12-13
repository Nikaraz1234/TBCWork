package com.example.tbcworks.presentation.screens.transaction.mapper

import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel

fun Transaction.toPresentation(): TransactionModel = TransactionModel(
    id = id,
    name = name,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)

fun TransactionModel.toDomain(): Transaction = Transaction(
    id = id,
    name = name,
    purpose = purpose,
    value = value,
    date = date,
    imageUrl = imageUrl
)