package com.example.tbcworks.data.model

import kotlinx.serialization.SerialName

data class LocationDto(
    val location: String,
    @SerialName("altitude_m")
    val altitude: Int,
    val title: String,
    val image: String,
    val stars: Int
)