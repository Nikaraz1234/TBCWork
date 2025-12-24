package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class SpeakerDto(
    val fullName: String,
    val role: String,
    val linkedinUrl: String,
    val websiteUrl: String,
    val description: String,
    val imgUrl: String
)