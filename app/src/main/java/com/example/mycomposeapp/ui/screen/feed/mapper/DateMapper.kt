package com.example.mycomposeapp.ui.screen.feed.mapper

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

fun Long.toPostDateString(): String {
    val dateTime = Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth
    val month = dateTime.month.name.lowercase()
        .replaceFirstChar { it.uppercase(Locale.ENGLISH) }

    val hour24 = dateTime.hour
    val minute = dateTime.minute.toString().padStart(2, '0')

    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }

    val amPm = if (hour24 < 12) "AM" else "PM"

    return "$day $month at $hour12:$minute $amPm"
}