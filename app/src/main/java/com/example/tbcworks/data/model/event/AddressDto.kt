package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val street: String,
    val city: String
)