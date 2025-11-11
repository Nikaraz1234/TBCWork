package com.example.tbcworks.common.auth

import retrofit2.Response

class AuthRepository(private val api: AuthApi) {

    suspend fun login(username: String, password: String): Result<Response<LoginResponse>> {
        return try {
            val response = api.login(LoginRequest(username, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, username: String, password: String): Result<Response<RegisterResponse>> {
        return try {
            val response = api.register(RegisterRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
