package com.example.tbcworks.presentation.screens.map.model

import kotlinx.serialization.Serializable


@Serializable
data class LocationModel(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)