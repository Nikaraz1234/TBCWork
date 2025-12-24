package com.example.tbcworks.presentation.screen.home.mapper

import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.presentation.model.EventDateModel
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
// Month short name: "JAN", "FEB"
fun EventDateModel.monthShort(): String {
    return startDate.month.name.take(3).uppercase(Locale.getDefault())
}

// Day of month
fun EventDateModel.day(): String = startDate.dayOfMonth.toString()

// Time range: "08:00 AM - 05:00 PM"
fun EventDateModel.toTimeRange(): String {
    return "${startDate.toFormattedTime()} - ${endDate.toFormattedTime()}"
}

// Optional: formatted register deadline
fun EventDateModel.registerDeadlineString(): String {
    return "${registerDeadline.dayOfMonth} ${registerDeadline.month.name.take(3).uppercase(Locale.getDefault())}, ${registerDeadline.year}, ${registerDeadline.toFormattedTime()}"
}

fun EventDate.toDisplayDate(): String {
    val month = startDate.month.name.take(3).capitalize(Locale.getDefault())
    val day = startDate.dayOfMonth
    val year = startDate.year
    return "$month $day, $year"
}

fun EventDateModel.toDisplayDate(): String {
    val month = startDate.month.name.take(3).capitalize(Locale.getDefault())
    val day = startDate.dayOfMonth
    val year = startDate.year
    return "$month $day, $year"
}

