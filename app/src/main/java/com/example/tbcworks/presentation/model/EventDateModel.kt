package com.example.tbcworks.presentation.screen.model

import kotlinx.datetime.LocalDateTime

data class EventDateModel(
    val eventType: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val registerDeadline: LocalDateTime
)