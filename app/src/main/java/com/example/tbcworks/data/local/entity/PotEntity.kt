package com.example.tbcworks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pots")
data class PotEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val amount: Double,
    val targetAmount: Double,
    val synced: Boolean = false
) {
    companion object
}
