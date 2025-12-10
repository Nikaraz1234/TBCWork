package com.example.tbcworks.presentation.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

fun Long.toDateString(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = local.dayOfMonth
    val month = local.month.name.lowercase(Locale.getDefault())
        .replaceFirstChar { it.uppercase() }

    val hour12 = when {
        local.hour == 0 -> 12
        local.hour > 12 -> local.hour - 12
        else -> local.hour
    }
    val minute = local.minute.toString().padStart(2, '0')
    val amPm = if (local.hour >= 12) "PM" else "AM"

    return "$day $month at $hour12:$minute $amPm"
}