package com.example.tbcworks.presentation.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.repository.RegisterRepository
import com.example.tbcworks.data.auth.repository.ResultWrapper
import com.example.tbcworks.data.dataStore.TokenDataStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: RegisterRepository,
    private val tokenStore: TokenDataStore
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.OnRegister ->
                viewModelScope.launch {
                    register(event.email, event.password, event.repeatPassword)
                }
        }
    }

    private suspend fun register(email: String, password: String, repeatPassword: String) {
        val error = validateInputs(email, password, repeatPassword)
        if (error != null) {
            _sideEffect.emit(RegisterSideEffect.ShowMessage(error))
            return
        }


        when (val result = repo.register(email, password)) {
            is ResultWrapper.Success -> {
                _sideEffect.emit(RegisterSideEffect.ToLogin(email, password))
            }
            is ResultWrapper.Error -> {
                _sideEffect.emit(RegisterSideEffect.ShowMessage(result.message))
            }
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

    companion object {
        private const val ERROR_EMPTY_FIELDS = "Fields cannot be empty"
        private const val INVALID_EMAIL_FORMAT = "Invalid email format"
        private const val NETWORK_ERROR = "Network Error"
        private const val UNKNOWN_ERROR = "Unknown Error"
        private const val PASSWORDS_DONT_MATCH = "Passwords don’t match"
    }
}

