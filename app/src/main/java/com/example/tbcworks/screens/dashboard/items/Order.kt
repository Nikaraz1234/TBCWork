package com.example.tbcworks.screens.dashboard.items

import java.io.Serializable

data class Order(
    val id: Int,
    val orderNumber: Int,
    val trackingNumber: String,
    val quantity: Int,
    val dateMillis: Long,
    val price: Int,
    var status: OrderStatus
) : Serializable

enum class OrderStatus {
    PENDING,
    DELIVERED,
    CANCELED
}

