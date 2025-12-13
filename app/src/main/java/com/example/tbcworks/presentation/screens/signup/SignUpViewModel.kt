package com.example.tbcworks.presentation.screens.signup

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.signup.SignUpUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(
    initialState = SignUpState()
) {

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUpClick -> signUp(event.email, event.password)
            SignUpEvent.OnLoginClick -> sendSideEffect(SignUpSideEffect.NavigateToLogin)
        }
    }

    private fun signUp(email: String, password: String) {
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            signUpUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    is Resource.Success -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(SignUpSideEffect.NavigateToHome)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false) }
                        sendSideEffect(
                            SignUpSideEffect.ShowError(result.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }
}
