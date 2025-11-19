package com.example.tbcworks.data.auth.repository

import com.example.tbcworks.data.auth.api.RegisterApi
import com.example.tbcworks.data.auth.dtos.RegisterRequestDto
import com.example.tbcworks.data.auth.dtos.RegisterResponseDto


class RegisterRepository(private val api: RegisterApi) : BaseRepository() {

    suspend fun register(email: String, password: String): ResultWrapper<RegisterResponseDto> {
        return safeApiCall {
            val response = api.register(RegisterRequestDto(email, password))

            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: "Registration failed")
            }
            response.body() ?: throw Exception("Registration failed")
        }
    }
}