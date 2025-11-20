package com.example.tbcworks.data.auth.repository

import com.example.tbcworks.data.auth.api.RegisterApi
import com.example.tbcworks.data.auth.dtos.RegisterRequestDto
import com.example.tbcworks.data.auth.dtos.RegisterResponseDto
import com.example.tbcworks.data.common.resource.HandleResponse
import javax.inject.Inject


class RegisterRepository @Inject constructor(
    private val api: RegisterApi,
    private val handleResponse: HandleResponse
) {

    suspend fun register(email: String, password: String) =
        handleResponse.safeApiCall {
            val response = api.register(RegisterRequestDto(email, password))

            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: REGISTRATION_FAILED)
            }
            response.body() ?: throw Exception(REGISTRATION_FAILED)
        }

    companion object {
        private const val REGISTRATION_FAILED = "Registration failed"
    }
}
