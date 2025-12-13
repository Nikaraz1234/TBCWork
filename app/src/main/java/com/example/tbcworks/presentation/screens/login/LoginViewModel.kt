package com.example.tbcworks.presentation.screens.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.datastore.SaveTokenUseCase
import com.example.tbcworks.domain.usecase.login.LoginUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginSideEffect, LoginEvent>(
    initialState = LoginState()
) {

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> login(event.email, event.password)
            LoginEvent.OnSignUpClick -> sendSideEffect(LoginSideEffect.NavigateToSignUp)
        }
    }

    private fun login(email: String, password: String) {
        setState { copy(isLoading = true) }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sendSideEffect(LoginSideEffect.ShowError("Invalid email"))
            return
        }

        if (password.length < 6) {
            sendSideEffect(LoginSideEffect.ShowError("Password too short"))
            return
        }

        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    is Resource.Success -> {
                        setState { copy(isLoading = false) }

                        result.data?.let { user ->
                            user.getIdToken(true).addOnSuccessListener { tokenResult ->
                                tokenResult.token?.let { token ->
                                    viewModelScope.launch {
                                        saveTokenUseCase(token)
                                    }
                                }
                            }
                        }

                        sendSideEffect(LoginSideEffect.NavigateToHome)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(
                            LoginSideEffect.ShowError(result.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }


}