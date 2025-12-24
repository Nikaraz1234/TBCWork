package com.example.tbcworks.data.model.event

data class NotificationsDto(
    val registerConfirmation: Boolean,
    val dailyReminder: Boolean,
    val hourReminder: Boolean,
    val waitlistUpdates: Boolean,
    val eventUpdated: Boolean
)