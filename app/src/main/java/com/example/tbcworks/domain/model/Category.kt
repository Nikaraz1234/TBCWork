package com.example.tbcworks.domain.model

data class Category(
    val id: String,
    val name: String,
    val nameDe: String?,
    val createdAt: String,
    val orderId: Int?,
    val children: List<Category>
)