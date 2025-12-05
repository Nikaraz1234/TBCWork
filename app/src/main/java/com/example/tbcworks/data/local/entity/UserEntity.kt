package com.example.tbcworks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val email: String,
    val activationStatus: Int,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long,
    val profileImageUrl: String?,
    val cachedImagePath: String?
)

