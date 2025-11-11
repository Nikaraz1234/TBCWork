package com.example.tbcworks.common.auth


import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val repo = AuthRepository(ApiClient.authApi)


    fun login(username: String, password: String){
        val error = validateInputs(username, password)
        if (error != null) {
            _uiState.value = AuthUiState.ShowMessage(error)
            return
        }

        _uiState.value = AuthUiState.Loading

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = repo.login(username, password)
                if(response.isSuccess){
                    _uiState.value = AuthUiState.LoginSuccess
                }else{
                    Log.e("AuthViewModel", "Register failed: $response")
                    _uiState.value = AuthUiState.ShowMessage("Unknown Error")
                }
            } catch (e: Exception){
                _uiState.value = AuthUiState.ShowMessage("Network Error")
            }
        }
    }

    suspend fun register(username: String, password: String, email: String): Boolean {
        val error = validateInputs(username, password, email)
        if (error != null) {
            _uiState.value = AuthUiState.ShowMessage(error)
            return false
        }

        _uiState.value = AuthUiState.Loading

        return try {
            val response = kotlinx.coroutines.withContext(Dispatchers.IO) {
                repo.register(username, password, email)
            }

            if (response.isSuccess) {
                _uiState.value = AuthUiState.RegisterSuccess
                true
            } else {
                Log.e("AuthViewModel", "Register failed: $response")
                _uiState.value = AuthUiState.ShowMessage("Unknown Error")
                false
            }
        } catch (e: Exception) {
            _uiState.value = AuthUiState.ShowMessage("Network Error")
            false
        }
    }



    private fun validateInputs(username: String, password: String, email: String = "", requireEmail: Boolean = false): String? {
        if (username.isBlank() || password.isBlank()) return "Username and password cannot be empty"
        if (requireEmail && email.isBlank()) return "Email cannot be empty"
        if (email.isNotBlank() && !isValidEmail(email)) return "Invalid email format"
        return null
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}