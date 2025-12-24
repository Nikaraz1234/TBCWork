package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class CapacityDto(
    val maxCapacity: Int,
    val currentlyRegistered: Int,
    val enableWaitlist: Boolean,
    val waitlistCapacity: Int,
    val autoApprove: Boolean
)