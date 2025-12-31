package com.example.tbcworks.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    val id: String,
    val name: String,
    @SerialName("name_de") val nameDe: String? = null,
    val createdAt: String,
    @SerialName("order_id") val orderId: Int? = 0,
    val bgl_number: String? = null,
    val bgl_variant: String? = null,
    val main: String? = null,
    val children: List<CategoryResponseDto> = emptyList()
)