package com.example.tbcworks.screens.dashboard.orders

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Order(
    val id: Int,
    val name: String,
    val colorName: String,
    @ColorRes val color: Int,
    @DrawableRes val image: Int,
    val quantity: Int,
    val price: Double,
    val status: OrderStatus = OrderStatus.ACTIVE,
    val reviews: MutableList<String> = mutableListOf()
)
enum class OrderStatus {
    ACTIVE,
    COMPLETED
}
