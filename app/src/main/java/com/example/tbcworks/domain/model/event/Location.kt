package com.example.tbcworks.domain.model.event

data class Location(
    val type: String,
    val venueName: String,
    val address: Address,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String
)