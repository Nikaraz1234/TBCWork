package com.example.tbcworks.data.model

data class LocationDto(
    val type: String,
    val venueName: String,
    val address: AddressDto,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String
)