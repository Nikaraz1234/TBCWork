package com.example.tbcworks.domain.model.event

data class Speaker(
    val fullName: String,
    val role: String,
    val linkedinUrl: String,
    val websiteUrl: String,
    val description: String,
    val imgUrl: String
)