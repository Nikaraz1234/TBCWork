package com.example.tbcworks.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)