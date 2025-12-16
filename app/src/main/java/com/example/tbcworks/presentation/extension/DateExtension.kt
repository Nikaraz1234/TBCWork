package com.example.tbcworks.presentation.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.toDate(pattern: String = "dd/MM, HH:mm"): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val hour = dateTime.hour.toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')

    return when(pattern) {
        "dd/MM, HH:mm" -> "$day/$month, $hour:$minute"
        "dd MMM, HH:mm" -> {
            val shortMonth = dateTime.month.name.take(3).lowercase()
                .replaceFirstChar { it.uppercase() }
            "$day $shortMonth, $hour:$minute"
        }
        else -> "$day/$month, $hour:$minute"
    }
}
