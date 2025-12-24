package com.example.tbcworks.presentation.screen.model

import kotlinx.datetime.LocalDateTime

data class AgendaModel(
    val startTime: LocalDateTime,
    val duration: String,
    val title: String,
    val description: String,
    val activityType: String,
    val activityLocation: String,
)