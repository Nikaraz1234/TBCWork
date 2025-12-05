package com.example.tbcworks.domain.model



sealed class ActivationStatus {
    object NotActive : ActivationStatus()
    object Online : ActivationStatus()
    class ActiveMinutesAgo(val minutes: Int) : ActivationStatus()
    class ActiveHoursAgo(val hours: Int) : ActivationStatus()
    object LongTimeInactive : ActivationStatus()
}