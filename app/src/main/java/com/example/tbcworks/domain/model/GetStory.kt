package com.example.tbcworks.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class GetStory(
    val title: String,
    val cover: String
)
