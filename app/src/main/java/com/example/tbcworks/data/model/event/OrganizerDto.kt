package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class OrganizerDto(
    val fullName: String,
    val jobTitle: String,
    val department: String,
    val profileImgUrl: String,
    val email: String
)