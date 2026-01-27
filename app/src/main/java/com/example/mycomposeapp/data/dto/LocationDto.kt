package com.example.mycomposeapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val title: String,
    val location: String,
    val number: String,
    val photo: String,
    val price: Int,
    val stars: Int
)
