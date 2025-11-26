package com.example.tbcworks.data.auth.repository

import com.example.tbcworks.data.auth.api.LoginApi
import com.example.tbcworks.data.auth.dtos.LoginRequestDto
import com.example.tbcworks.data.auth.dtos.LoginResponseDto
import com.example.tbcworks.data.common.resource.HandleResponse
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val handleResponse: HandleResponse
) {

    fun login(email: String, password: String) =
        handleResponse.safeApiCall {
            val response = api.login(LoginRequestDto(email, password))

            if (!response.isSuccessful) {
                throw Exception(response.errorBody()?.string() ?: INVALID_CREDENTIALS)
            }
            response.body() ?: throw Exception(INVALID_CREDENTIALS)
        }

    companion object {
        private const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}