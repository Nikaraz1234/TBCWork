package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dtos.LoginResponseDto
import com.example.tbcworks.domain.model.GetLoginResponse

fun LoginResponseDto.toDomain() = GetLoginResponse(
    token = token ?: ""
)