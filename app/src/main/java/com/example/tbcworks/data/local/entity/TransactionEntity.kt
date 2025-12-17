package com.example.tbcworks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val senderId: String,
    val receiverEmail: String,
    val purpose: String,
    val value: Double,
    val date: String,
    val imageUrl: String? = null,
    val synced: Boolean = false
)
