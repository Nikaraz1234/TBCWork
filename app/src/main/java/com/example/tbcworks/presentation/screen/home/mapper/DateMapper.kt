package com.example.tbcworks.presentation.screen.mapper

import com.example.tbcworks.domain.model.EventDate
import kotlinx.datetime.LocalDateTime
import java.util.Locale

// Month short name: "JAN", "FEB"
fun EventDate.monthShort(): String {
    return startDate.month.name.take(3).uppercase(Locale.getDefault())
}

// Day of month
fun EventDate.day(): String = startDate.dayOfMonth.toString()

// Time range: "08:00 AM - 05:00 PM"
fun EventDate.toTimeRange(): String {
    return "${startDate.toFormattedTime()} - ${endDate.toFormattedTime()}"
}

// Extension to format LocalDateTime to "hh:mm AM/PM"
fun LocalDateTime.toFormattedTime(): String {
    val hour12 = if (hour == 0 || hour == 12) 12 else hour % 12
    val amPm = if (hour < 12) "AM" else "PM"
    val minuteStr = minute.toString().padStart(2, '0')
    return "$hour12:$minuteStr $amPm"
}

// Optional: formatted register deadline
fun EventDate.registerDeadlineString(): String {
    return "${registerDeadline.dayOfMonth} ${registerDeadline.month.name.take(3).uppercase(Locale.getDefault())}, ${registerDeadline.year}, ${registerDeadline.toFormattedTime()}"
}