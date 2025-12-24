package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.auth.sign_in.SignInResponseDto
import com.example.tbcworks.domain.model.auth.AuthToken

fun SignInResponseDto.toDomain(): AuthToken {
    return AuthToken(token)
}
