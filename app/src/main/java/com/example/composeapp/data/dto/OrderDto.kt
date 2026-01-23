package com.example.composeapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: Int,
    @SerialName("order_number")
    val orderNumber: String,
    val date: String,
    @SerialName("tracking_number")
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Int,
    val status: String
)
