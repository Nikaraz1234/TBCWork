package com.example.composeapp.data.mapper

import com.example.composeapp.data.dto.OrderDto
import com.example.composeapp.domain.model.Order

fun OrderDto.toDomain(): Order = Order(
    id = id,
    orderNumber = orderNumber,
    date = date,
    trackingNumber = trackingNumber,
    quantity = quantity,
    subtotal = subtotal,
    status = status
)