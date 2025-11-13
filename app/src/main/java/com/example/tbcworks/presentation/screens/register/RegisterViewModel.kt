package com.example.tbcworks.presentation.screens.register

import androidx.lifecycle.ViewModel
import com.example.tbcworks.data.auth.AuthRepository
import com.example.tbcworks.data.auth.AuthUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class RegisterViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    suspend fun register(username: String, password: String, email: String): Boolean {
        val error = validateInputs(username, password, email)
        if (error != null) {
            _uiState.value = AuthUiState.ShowMessage(error)
            return false
        }

        _uiState.value = AuthUiState.Loading

        return try {
            val response = withContext(Dispatchers.IO) {
                repo.register(email, password)
            }

            if (response.isSuccess) {
                _uiState.value = AuthUiState.RegisterSuccess
                true
            } else {
                val errorMessage = response.exceptionOrNull()?.message ?: UNKNOWN_ERROR
                _uiState.value = AuthUiState.ShowMessage(errorMessage)
                false
            }
        } catch (e: Exception) {
            _uiState.value = AuthUiState.ShowMessage(NETWORK_ERROR)
            false
        }
    }

    private fun validateInputs(username: String, password: String, email: String): String? {
        if (username.isBlank() || password.isBlank()) return ERROR_EMPTY_FIELDS
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return INVALID_EMAIL_FORMAT
        }
        return null
    }

    companion object{
        private const val ERROR_EMPTY_FIELDS = "Username and password cannot be empty"
        private const val INVALID_EMAIL_FORMAT = "Invalid email format"
        private const val NETWORK_ERROR = "Network Error"
        private const val UNKNOWN_ERROR = "Unknown Error"
    }
}
