package com.example.tbcworks.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.example.tbcworks.data.auth.AuthRepository
import com.example.tbcworks.data.auth.AuthUiState
import com.example.tbcworks.data.auth.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        val error = validateInputs(username, password)
        if (error != null) return Result.failure(Exception(error))

        return try {
            repo.login(username, password)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateInputs(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return FIELD_EMPTY
        return null
    }
    companion object{
        private const val FIELD_EMPTY = "Username and password cannot be empty"
    }
}
