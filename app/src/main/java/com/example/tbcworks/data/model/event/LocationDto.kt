package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val type: String,
    val venueName: String,
    val address: AddressDto,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String
)