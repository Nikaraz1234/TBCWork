package com.example.tbcworks.data.repository

import com.example.tbcworks.data.api.RegisterApi
import com.example.tbcworks.data.dtos.RegisterRequestDto
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.mapper.RegisterMapper
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi,
    private val handleResponse: HandleResponse
    private val mapper: RegisterMapper
) {

    fun register(email: String, password: String) =
        handleResponse.safeApiCall {
            val response = api.register(RegisterRequestDto(email, password))

            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: REGISTRATION_FAILED)
            }
            response.body()?.let { mapper.map(it) }
                ?: throw Exception(REGISTRATION_FAILED)
        }

    companion object {
        private const val REGISTRATION_FAILED = "Registration failed"
    }
}
