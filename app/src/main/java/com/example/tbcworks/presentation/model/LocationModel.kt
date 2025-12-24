package com.example.tbcworks.presentation.model

data class LocationModel(
    val type: String,
    val venueName: String,
    val address: AddressModel,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String
)