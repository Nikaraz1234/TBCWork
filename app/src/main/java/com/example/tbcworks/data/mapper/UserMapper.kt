package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.local.entity.UserEntity
import com.example.tbcworks.data.model.UserDto
import com.example.tbcworks.domain.model.User

fun UserDto.toEntity() = UserEntity(
    id = id,
    fullName = fullName,
    email = email,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    profileImageUrl = profileImageUrl,
    cachedImagePath = null,
)

fun UserDto.toDomain() = User(
    id = id,
    fullName = fullName,
    email = email,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    profileImageUrl = profileImageUrl,
    cachedImagePath = null,
)

fun UserEntity.toDomain() = User(
    id = id,
    fullName = fullName,
    email = email,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    profileImageUrl = profileImageUrl,
    cachedImagePath = null,
)