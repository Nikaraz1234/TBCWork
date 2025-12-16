package com.example.tbcworks.presentation.screens.transaction.mapper

import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel


fun Transaction.toPresentation(): TransactionModel {
    return TransactionModel(
        id = id,
        senderId = senderId,
        receiverEmail = receiverEmail,
        purpose = purpose,
        value = value,
        date = date,
        imageUrl = imageUrl
    )
}
fun TransactionModel.toDomain(): Transaction {
    return Transaction(
        id = id,
        senderId = senderId,
        receiverEmail = receiverEmail,
        purpose = purpose,
        value = value,
        date = date,
        imageUrl = imageUrl
    )
}
