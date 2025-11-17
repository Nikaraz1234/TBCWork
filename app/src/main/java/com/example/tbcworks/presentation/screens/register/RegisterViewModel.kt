package com.example.tbcworks.presentation.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.AuthRepository
import com.example.tbcworks.data.auth.AuthUiState
import com.example.tbcworks.data.dataStore.TokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val repo: AuthRepository,
    private val tokenStore: TokenDataStore
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<AuthUiState>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    suspend fun register(email: String, password: String, repeatPassword: String): Boolean {
        val error = validateInputs(email, password, repeatPassword)
        if (error != null) {
            viewModelScope.launch {
                _uiEvent.emit(AuthUiState.ShowMessage(error))
            }
            return false
        }

        viewModelScope.launch {
            _uiEvent.emit(AuthUiState.Loading)
        }

        return try {
            val response = withContext(Dispatchers.IO) {
                repo.register(email, password)
            }

            response.fold(
                onSuccess = { _ ->
                    viewModelScope.launch {
                        _navigationEvent.emit(NavigationEvent.ToLogin(email, password))
                    }
                    true
                },
                onFailure = { exception ->
                    viewModelScope.launch {
                        _uiEvent.emit(AuthUiState.ShowMessage(exception.message ?: UNKNOWN_ERROR))
                    }
                    false
                }
            )
        } catch (e: Exception) {
            viewModelScope.launch { _uiEvent.emit(AuthUiState.ShowMessage(NETWORK_ERROR)) }
            false
        }
    }

    private fun validateInputs(email: String, password: String, repeatPassword: String): String? {
        return if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            ERROR_EMPTY_FIELDS
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            INVALID_EMAIL_FORMAT
        } else if (password != repeatPassword) {
            PASSWORDS_DONT_MATCH
        } else {
            null
        }
    }

    sealed class NavigationEvent {
        data class ToLogin(val username: String, val password: String) : NavigationEvent()
    }

    companion object {
        private const val ERROR_EMPTY_FIELDS = "Username and password cannot be empty"
        private const val INVALID_EMAIL_FORMAT = "Invalid email format"
        private const val NETWORK_ERROR = "Network Error"
        private const val UNKNOWN_ERROR = "Unknown Error"
        private const val PASSWORDS_DONT_MATCH = "Passwords don’t match"
    }
}

