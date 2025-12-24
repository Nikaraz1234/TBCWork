package com.example.tbcworks.domain.model

data class Capacity(
    val maxCapacity: Int,
    val currentlyRegistered: Int,
    val enableWaitlist: Boolean,
    val waitlistCapacity: Int,
    val autoApprove: Boolean
)