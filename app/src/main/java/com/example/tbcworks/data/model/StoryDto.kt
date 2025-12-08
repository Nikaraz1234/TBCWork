package com.example.tbcworks.data.model

import kotlinx.serialization.Serializable


@Serializable
data class StoryDto(
    val title: String,
    val cover: String
)

