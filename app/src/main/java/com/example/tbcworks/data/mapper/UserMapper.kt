package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dtos.UserResponseDto
import com.example.tbcworks.domain.model.GetUser

fun UserResponseDto.UserDto.toDomain() = GetUser(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar
)