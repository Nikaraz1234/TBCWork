package com.example.tbcworks.domain.model.event

import kotlinx.datetime.LocalDateTime


data class EventDate(
    val eventType: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val registerDeadline: LocalDateTime
)