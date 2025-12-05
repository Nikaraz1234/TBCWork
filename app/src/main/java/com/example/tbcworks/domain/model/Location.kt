package com.example.tbcworks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val location: String,
    val altitude: Int,
    val title: String,
    val image: String,
    val stars: Int
)