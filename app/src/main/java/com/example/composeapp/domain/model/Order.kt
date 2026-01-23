package com.example.composeapp.domain.model


data class Order(
    val id: Int,
    val orderNumber: String,
    val date: String,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Int,
    val status: String
)
