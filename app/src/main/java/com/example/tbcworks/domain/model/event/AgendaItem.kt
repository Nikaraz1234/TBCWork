package com.example.tbcworks.domain.model

import kotlinx.datetime.LocalDateTime


data class AgendaItem(
    val startTime: LocalDateTime,
    val duration: String,
    val title: String,
    val description: String,
    val activityType: String,
    val activityLocation: String,
    val additionalInfo: List<AgendaAdditionalInfo>
)