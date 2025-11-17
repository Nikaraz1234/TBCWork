package com.example.tbcworks.data.auth

import com.example.tbcworks.data.auth.api.LoginApi
import com.example.tbcworks.data.auth.api.RegisterApi
import com.example.tbcworks.data.auth.dtos.LoginRequestDto
import com.example.tbcworks.data.auth.dtos.LoginResponseDto
import com.example.tbcworks.data.auth.dtos.RegisterRequestDto
import com.example.tbcworks.data.auth.dtos.RegisterResponseDto

class AuthRepository(private val loginApi: LoginApi, private val registerApi: RegisterApi) {

    suspend fun login(email: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = loginApi.login(LoginRequestDto(email, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.token != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception(INVALID_CREDENTIALS))
                }
            } else {
                val error = response.errorBody()?.string() ?: UNKNOWN_ERROR
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun register(email: String, password: String): Result<RegisterResponseDto> {
        return try {
            val response = registerApi.register(RegisterRequestDto(email, password))
            if (response.isSuccessful && response.body()?.token != null) {
                Result.success(response.body()!!)
            } else {
                val error = response.errorBody()?.string() ?: REG_FAILED_MSG
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    companion object{
        private const val REG_FAILED_MSG = "Registration failed"
        private const val UNKNOWN_ERROR = "Unknown Error"
        private const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}
