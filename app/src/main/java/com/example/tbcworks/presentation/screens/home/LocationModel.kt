package com.example.tbcworks.presentation.screens.home

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val location: String,
    val altitude: Int,
    val title: String,
    val image: String,
    val stars: Int
)
