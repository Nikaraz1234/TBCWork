package com.example.tbcworks.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.AuthRepository
import com.example.tbcworks.data.auth.AuthUiState
import com.example.tbcworks.data.dataStore.TokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val repo: AuthRepository,
    private val tokenStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    suspend fun login(email: String, password: String, rememberMe: Boolean): Boolean {
        val error = validateInputs(email, password)
        if (error != null) {
            _uiState.value = AuthUiState.ShowMessage(error)
            return false
        }

        _uiState.value = AuthUiState.Loading

        return try {
            val response = withContext(Dispatchers.IO) {
                repo.login(email, password)
            }

            response.fold(
                onSuccess = { response ->
                    if (rememberMe) {
                        response.token?.let { token ->
                            tokenStore.saveToken(token)
                            tokenStore.saveEmail(email)

                        }
                    }

                    viewModelScope.launch {
                        _navigationEvent.emit(NavigationEvent.ToHome)
                    }
                    true
                },
                onFailure = { exception ->
                    _uiState.value = AuthUiState.ShowMessage(
                        exception.message ?: UNKNOWN_ERROR
                    )
                    false
                }
            )
        } catch (e: Exception) {
            _uiState.value = AuthUiState.ShowMessage(NETWORK_ERROR)
            false
        }
    }
    fun navigateToRegister(){
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ToRegister)
        }
    }

    private fun validateInputs(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return FIELD_EMPTY
        return null
    }
    companion object{
        private const val FIELD_EMPTY = "Username and password cannot be empty"
        private const val UNKNOWN_ERROR = "Unknown error"
        private const val NETWORK_ERROR = "NETWORK_ERROR"
    }

    sealed class NavigationEvent {
        object ToHome : NavigationEvent()
        object ToRegister : NavigationEvent()
    }
}
