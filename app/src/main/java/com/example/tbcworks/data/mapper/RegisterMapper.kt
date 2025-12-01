package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dtos.RegisterResponseDto
import com.example.tbcworks.domain.model.GetRegisterResponse

fun RegisterResponseDto.toDomain(): GetRegisterResponse {
    return GetRegisterResponse(
        id = this.id,
        token = this.token
    )
}