package com.example.composeapp.ui.screen.dashboard.model

data class OrderModel(
    val id: Int,
    val orderNumber: String,
    val date: String,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Int,
    val status: OrderStatus
)