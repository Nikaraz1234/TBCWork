package com.example.tbcworks.presentation.model

data class CapacityModel(
    val maxCapacity: Int,
    val currentlyRegistered: Int,
    val enableWaitlist: Boolean,
    val waitlistCapacity: Int,
    val autoApprove: Boolean
)