package com.example.tbcworks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)