package com.example.tbcworks.data.auth

class AuthRepository(private val api: AuthApi) {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
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


    suspend fun register(email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = api.register(RegisterRequest(email, password))
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
