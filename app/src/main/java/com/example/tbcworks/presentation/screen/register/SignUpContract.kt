package com.example.tbcworks.presentation.screen.register

import com.example.tbcworks.presentation.screen.register.model.SignUpModel

object SignUpContract {
    data class SignUpState(
        val errors: Map<Field, String> = emptyMap(),  // store field-specific validation errors
        val isLoading: Boolean = false
    ) {
        enum class Field {
            FIRST_NAME, LAST_NAME, EMAIL, PHONE, OTP, PASSWORD, CONFIRM_PASSWORD, POLICY, DEPARTMENT
        }
    }

    sealed class SignUpSideEffect {
        data class ShowMessage(val message: String) : SignUpSideEffect()
        object NavigateToHome : SignUpSideEffect()
        object NavigateToSignIn : SignUpSideEffect()
    }
    sealed class SignUpEvent {
        data class Submit(val model: SignUpModel) : SignUpEvent()
        object SignInClicked : SignUpEvent()
    }

}