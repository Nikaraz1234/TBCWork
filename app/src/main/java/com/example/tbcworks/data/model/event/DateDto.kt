package com.example.tbcworks.data.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    val eventType: String,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val startDate: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val endDate: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val registerDeadline: LocalDateTime
)