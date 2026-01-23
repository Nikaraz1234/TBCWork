package com.example.composeapp.ui.screen.dashboard.mapper

import com.example.composeapp.domain.model.Order
import com.example.composeapp.ui.screen.dashboard.model.OrderModel
import com.example.composeapp.ui.screen.dashboard.model.OrderStatus

fun Order.toPresentation(): OrderModel = OrderModel(
    id = id,
    orderNumber = orderNumber,
    date = date,
    trackingNumber = trackingNumber,
    quantity = quantity,
    subtotal = subtotal,
    when (status.uppercase()) {
        "PENDING" -> OrderStatus.PENDING
        "DELIVERED" -> OrderStatus.DELIVERED
        "CANCELED" -> OrderStatus.CANCELED
        else -> OrderStatus.PENDING
    }
)