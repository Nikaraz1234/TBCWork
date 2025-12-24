package com.example.tbcworks.data.model


data class AgendaItemDto(
    val startTime: String,
    val duration: String,
    val title: String,
    val description: String,
    val activityType: String,
    val activityLocation: String,
    val additionalInfo: List<AgendaAdditionalInfoDto>
)